package br.com.brunadelmouro.cursospringboot.dto;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //dependency - pom.xml
    @NotEmpty(message="Preenchimento obrigatório")
    @Length(min=5, max=80, message="O tamanho deve ser entre 5 e 80 caracteres")
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
