package br.com.brunadelmouro.cursospringboot.services;

//layer - service

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.repositories.CustomerRepository;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
