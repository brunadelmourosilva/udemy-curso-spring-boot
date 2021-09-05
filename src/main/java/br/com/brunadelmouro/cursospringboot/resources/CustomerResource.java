package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/customers")
public class CustomerResource {

    @Autowired
    CustomerService service;

    @RequestMapping(value="/{id}", method= RequestMethod.GET) // HTTP request
    public ResponseEntity<?> find(@PathVariable Integer id){
        Customer obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }
}
