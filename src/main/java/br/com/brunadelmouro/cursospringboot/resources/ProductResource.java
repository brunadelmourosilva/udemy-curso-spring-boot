package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Product;
import br.com.brunadelmouro.cursospringboot.domain.Request;
import br.com.brunadelmouro.cursospringboot.dto.CategoryDTO;
import br.com.brunadelmouro.cursospringboot.dto.ProductDTO;
import br.com.brunadelmouro.cursospringboot.resources.utils.URL;
import br.com.brunadelmouro.cursospringboot.services.ProductService;
import br.com.brunadelmouro.cursospringboot.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/products")
public class ProductResource {

    @Autowired
    ProductService service;

    @RequestMapping(value="/{id}", method = RequestMethod.GET) // HTTP request
    public ResponseEntity<Product> find(@PathVariable Integer id){
        Product obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method=RequestMethod.GET) // HTTP request
    public ResponseEntity<Page<ProductDTO>> findPage(@RequestParam(value="name", defaultValue="") String name,
                                                     @RequestParam(value="categories", defaultValue="") String categories,
                                                     @RequestParam(value="page", defaultValue="0") Integer page,
                                                     @RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
                                                     @RequestParam(value="orderBy", defaultValue="name") String orderBy,
                                                     @RequestParam(value="direction", defaultValue="ASC") String direction) {


        //conversions
        String nameDecoded = URL.decodeParam(name);
        List<Integer> ids = URL.decodeIntList(categories);

        Page<Product> list = service.search(nameDecoded, ids, page, linesPerPage, orderBy, direction);

        Page<ProductDTO> listDto = list.
                                   map(obj -> new ProductDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }
}
