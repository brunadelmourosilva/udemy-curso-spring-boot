package br.com.brunadelmouro.cursospringboot.services;

//layer - service

import br.com.brunadelmouro.cursospringboot.domain.Address;
import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.domain.enums.Profile;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerNewDTO;
import br.com.brunadelmouro.cursospringboot.repositories.AddressRepository;
import br.com.brunadelmouro.cursospringboot.repositories.CustomerRepository;
import br.com.brunadelmouro.cursospringboot.security.UserSS;
import br.com.brunadelmouro.cursospringboot.services.exception.AuthorizationException;
import br.com.brunadelmouro.cursospringboot.services.exception.DataIntegrityException;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Value("${img.profile.size}")
    private Integer size;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Autowired
    ImageService imageService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    CustomerRepository repository;

    @Autowired
    AddressRepository addressRepository;

    public Customer find(Integer id){

        UserSS user = UserService.authenticated();
        if (user==null || !user.hasRole(Profile.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Access denied");
        }

        Optional<Customer> obj = repository.findById(id);

        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Customer.class.getName())
        );
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer findByEmail(String email){
        UserSS user = UserService.authenticated();

        if (user == null || !user.hasRole(Profile.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Access denied");
        }

        Customer obj = repository.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Customer.class.getName());
        }
        return obj;
    }

    @Transactional
    public Customer insert(Customer obj){
        obj.setId(null); //"null" id because it inserts a new object
        obj = repository.save(obj);
        addressRepository.saveAll(obj.getAddresses());

        return obj;
    }

    public Customer update(Customer obj){
        Customer newObj = find(obj.getId()); //exception - if the object does not exist
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    public void delete(Integer id){
        find(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){

            throw new DataIntegrityException("Cannot delete because there are relation requests");
        }
    }

    //Page with optional parameters
    public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repository.findAll(pageRequest);
    }

    //convert DTO to newCustomerDTO
    public Customer fromDTO(CustomerNewDTO customerNewDTO) {
        Customer customer = new Customer(null, customerNewDTO.getName(), customerNewDTO.getEmail(), customerNewDTO.getCpfOrCnpj(), CustomerType.toEnum(customerNewDTO.getCustomerType()), pe.encode(customerNewDTO.getPassword()));
        City city = new City(customerNewDTO.getCityId(), null, null);
        Address address = new Address(null, customerNewDTO.getPatio(), customerNewDTO.getNumber(), customerNewDTO.getComplement(), customerNewDTO.getNeighborhood(), customerNewDTO.getZipCode(), customer, city);

        customer.getAddresses().add(address);
        customer.getPhones().add(customerNewDTO.getPhone1());

        if(customerNewDTO.getPhone2() != null){
            customer.getPhones().add(customerNewDTO.getPhone2());
        }

        if(customerNewDTO.getPhone3() != null){
            customer.getPhones().add(customerNewDTO.getPhone3());
        }

        return customer;
    }


    //convert DTO to Customer
    public Customer fromDTO(CustomerDTO objDto) {
        return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null, null);
    }

    private void updateData(Customer newObj, Customer obj){
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Access denied");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

        //crop image
        jpgImage = imageService.cropSquare(jpgImage);

        //resize image
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }



}
