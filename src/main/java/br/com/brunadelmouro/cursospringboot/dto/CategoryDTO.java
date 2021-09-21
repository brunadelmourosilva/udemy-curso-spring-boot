package br.com.brunadelmouro.cursospringboot.dto;

import br.com.brunadelmouro.cursospringboot.domain.Category;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    public CategoryDTO() {
    }

    //convert a category list to DTO
    public CategoryDTO(Category category) {
        id = category.getId();
        name = category.getName();
    }

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
