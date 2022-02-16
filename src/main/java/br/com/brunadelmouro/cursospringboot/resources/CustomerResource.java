package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerNewDTO;
import br.com.brunadelmouro.cursospringboot.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/customers")
public class CustomerResource {

    @Autowired
    CustomerService service;

    // HTTP status code 201(created)
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerNewDTO customerNewDTO){ //receive a category in json format
        Customer obj = service.fromDTO(customerNewDTO); //convert

        obj = service.insert(obj);
        //get a new id as argument to URI
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    // HTTP status code 204(success - no content)
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody CustomerDTO objDto, @PathVariable Integer id){
        Customer obj = service.fromDTO(objDto); //convert

        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    // HTTP status code 204(success - no content)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    // case use - step 7
    @RequestMapping(value="/{id}", method= RequestMethod.GET) // HTTP request
    public ResponseEntity<Customer> find(@PathVariable Integer id){
        Customer obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method=RequestMethod.GET) // HTTP request
    public ResponseEntity<List<CustomerDTO>> findAll(){
        List<Customer> list = service.findAll();

        //convert a list to other list
        List<CustomerDTO> listDto = list.
                stream().
                map(obj -> new CustomerDTO(obj)).
                collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value="/page", method=RequestMethod.GET) // HTTP request
    public ResponseEntity<Page<CustomerDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page,
                                                      @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
                                                      @RequestParam(value="orderBy", defaultValue="name") String orderBy,
                                                      @RequestParam(value="direction", defaultValue="ASC") String direction) {

        Page<Customer> list = service.findPage(page, linesPerPage, orderBy, direction);

        Page<CustomerDTO> listDto = list.
                map(obj -> new CustomerDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/picture", method=RequestMethod.POST)
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
        URI uri = service.uploadProfilePicture(file);

        return ResponseEntity.created(uri).build();
    }

}
