package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.repositories.CategoryRepository;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//layer - service

@Service
public class CategoryService {

    @Autowired //dependency injection - instance
    private CategoryRepository repository;

    public Category find(Integer id){
        Optional<Category> obj = repository.findById(id);

        //invalid id - exception
        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Category.class.getName())
        );
    }

    public Category insert(Category obj){
        obj.setId(null); //"null" id because it inserts a new object

        return repository.save(obj);
    }

    public Category update(Category obj){
        find(obj.getId()); //exception - if the object does not exist

        return repository.save(obj);
    }
}
