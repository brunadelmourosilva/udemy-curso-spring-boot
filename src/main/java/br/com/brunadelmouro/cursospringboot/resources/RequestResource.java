package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Request;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/requests")
public class RequestResource {

    @Autowired
    RequestService requestService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET) // HTTP request
    public ResponseEntity<Request> find(@PathVariable Integer id){
        Request obj = requestService.find(id);

        return ResponseEntity.ok().body(obj);
    }

    // HTTP status code 201(created)
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Request objRequest){ //receive a category in json format
        objRequest = requestService.insert(objRequest);
        //get a new id as argument to URI
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(objRequest.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
