package br.com.brunadelmouro.cursospringboot.services;

//layer - service

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerDTO;
import br.com.brunadelmouro.cursospringboot.repositories.CustomerRepository;
import br.com.brunadelmouro.cursospringboot.services.exception.DataIntegrityException;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository repository;

    public Customer find(Integer id){
        Optional<Customer> obj = repository.findById(id);

        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Customer.class.getName())
        );
    }

    public List<Customer> findAll(){
        return repository.findAll();
    }

    public Customer insert(Customer obj){
        obj.setId(null); //"null" id because it inserts a new object

        return repository.save(obj);
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

            throw new DataIntegrityException("Cannot delete because there are relation entities");
        }
    }

    //Page with optional parameters
    public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repository.findAll(pageRequest);
    }

    //convert DTO to Customer
    public Customer fromDTO(CustomerDTO objDto) {
        return new Customer(objDto.getId(), objDto.getName(), objDto.getEmail(), null, null);
    }

    private void updateData(Customer newObj, Customer obj){
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
    }
}
