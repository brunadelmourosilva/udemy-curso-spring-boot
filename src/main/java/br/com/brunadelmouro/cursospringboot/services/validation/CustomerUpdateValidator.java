package br.com.brunadelmouro.cursospringboot.services.validation;

import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.dto.CustomerDTO;
import br.com.brunadelmouro.cursospringboot.dto.CustomerNewDTO;
import br.com.brunadelmouro.cursospringboot.repositories.CustomerRepository;
import br.com.brunadelmouro.cursospringboot.resources.exception.FieldMessage;
import br.com.brunadelmouro.cursospringboot.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.persistence.ManyToOne;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDTO> {

    //get a URI parameter(id to update)
    @Autowired
    private HttpServletRequest request;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void initialize(CustomerUpdate ann) {
    }

    @Override
    public boolean isValid(CustomerDTO objDto, ConstraintValidatorContext context) {

        //requests -> attributes
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Customer aux = customerRepository.findByEmail(objDto.getEmail());


        //if it performs an update where there is already an email in another record
        if(aux != null && !aux.getId().equals(uriId)){
            list.add(new FieldMessage("email", "e-mail already existing"));
        }

        //inserting each new message error
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
