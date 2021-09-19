package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//layer - rest controllers

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

    @Autowired
    CategoryService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET) // HTTP request
    public ResponseEntity<Category> find(@PathVariable Integer id){
        Category obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    // HTTP status code 201(created)
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Category obj){ //receive a category in json format
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
    public ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Category obj){
        obj.setId(id);
        obj = service.update(obj);

        return ResponseEntity.noContent().build();
    }

    // HTTP status code 204(success - no content)
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }


}
