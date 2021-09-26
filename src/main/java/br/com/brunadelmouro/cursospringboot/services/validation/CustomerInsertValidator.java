package br.com.brunadelmouro.cursospringboot.services.validation;

import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.dto.CustomerNewDTO;
import br.com.brunadelmouro.cursospringboot.repositories.CustomerRepository;
import br.com.brunadelmouro.cursospringboot.resources.exception.FieldMessage;
import br.com.brunadelmouro.cursospringboot.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void initialize(CustomerInsert ann) {
    }

    @Override
    public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getCustomerType().equals(CustomerType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "invalid CPF"));
        }

        if(objDto.getCustomerType().equals(CustomerType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())){
            list.add(new FieldMessage("cpfOrCnpj", "invalid CNPF"));
        }

        Customer aux = customerRepository.findByEmail(objDto.getEmail());

        if(aux != null){
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
