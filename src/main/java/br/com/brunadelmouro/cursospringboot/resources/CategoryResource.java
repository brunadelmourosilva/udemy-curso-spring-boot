package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

//layer - rest controllers

@RestController
@RequestMapping(value="/categories")
public class CategoryResource {

    @Autowired
    CategoryService service;

    // HTTP status code 201(created)
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryDTO objCategoryDTO){ //receive a category in json format
        Category obj = service.fromDTO(objCategoryDTO); //convert

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
    public ResponseEntity<Void> update(@Valid @RequestBody CategoryDTO objDto, @PathVariable Integer id){
        Category obj = service.fromDTO(objDto); //convert

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

    @RequestMapping(method=RequestMethod.GET) // HTTP request
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<Category> list = service.findAll();

        //convert a list to other list
        List<CategoryDTO> listDto = list.
                                    stream().
                                    map(obj -> new CategoryDTO(obj)).
                                    collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/page", method=RequestMethod.GET) // HTTP request
    public ResponseEntity<Page<CategoryDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page,
                                                      @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
                                                      @RequestParam(value="orderBy", defaultValue="name") String orderBy,
                                                      @RequestParam(value="direction", defaultValue="ASC") String direction) {

        Page<Category> list = service.findPage(page, linesPerPage, orderBy, direction);

        Page<CategoryDTO> listDto = list.
                                    map(obj -> new CategoryDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }




}
