package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.repositories.CategoryRepository;
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

    public List<Category> findAll(){
        return repository.findAll();
    }

    public Category insert(Category obj){
        obj.setId(null); //"null" id because it inserts a new object

        return repository.save(obj);
    }

    public Category update(Category obj){
        find(obj.getId()); //exception - if the object does not exist

        return repository.save(obj);
    }

    public void delete(Integer id){
        find(id);

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e){

            throw new DataIntegrityException("Cannot delete a category that has products");
        }
    }

    //Page with optional parameters
    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        return repository.findAll(pageRequest);
    }
}
