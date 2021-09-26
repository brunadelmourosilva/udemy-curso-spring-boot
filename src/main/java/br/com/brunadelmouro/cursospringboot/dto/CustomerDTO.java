package br.com.brunadelmouro.cursospringboot.dto;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.services.validation.CustomerUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@CustomerUpdate
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // I can replace
    private Integer id;

    @NotEmpty(message="Preenchimento obrigatório")
    @Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
    private String name;

    @NotEmpty(message="Preenchimento obrigatório")
    @Email(message="E-mail inválido")
    private String email;

    public CustomerDTO() {
    }

    public CustomerDTO(Customer obj) {
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
