package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {


    @RequestMapping(method= RequestMethod.GET) // HTTP request
    public List<Category> list(){

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1,"Informática"));
        categories.add(new Category(2,"Escritório"));

        return categories;
    }
}
