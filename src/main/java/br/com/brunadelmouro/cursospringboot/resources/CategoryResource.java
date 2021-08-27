package br.com.brunadelmouro.cursospringboot.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {


    @RequestMapping(method= RequestMethod.GET) // HTTP request
    public String listar(){
        return "REST is working";
    }
}
