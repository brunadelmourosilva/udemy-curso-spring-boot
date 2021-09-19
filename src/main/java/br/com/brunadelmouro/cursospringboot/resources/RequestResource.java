package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Request;
import br.com.brunadelmouro.cursospringboot.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

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
}
