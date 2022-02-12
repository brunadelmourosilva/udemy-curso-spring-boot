package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.*;
import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;
import br.com.brunadelmouro.cursospringboot.repositories.PaymentRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestItemRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestRepository;
import br.com.brunadelmouro.cursospringboot.security.UserSS;
import br.com.brunadelmouro.cursospringboot.services.exception.AuthorizationException;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    BilletService billetService;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    RequestItemRepository requestItemRepository;

    @Autowired
    EmailService emailService;


    public Request find(Integer id){
        Optional<Request> obj = requestRepository.findById(id);

        System.out.println();

        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Request.class.getName())
        );
    }

    public Request insert(Request objRequest){
        objRequest.setId(null);
        objRequest.setDate(new Date());
        objRequest.setCustomer(customerService.find(objRequest.getCustomer().getId())); //find as client object JSON
        objRequest.getPayment().setStatus(StatusPayment.PENDENTE);

        objRequest.getPayment().setRequest(objRequest);

        if(objRequest.getPayment() instanceof PaymentBillet){
            PaymentBillet pb = (PaymentBillet) objRequest.getPayment();
            billetService.fillPaymentWithBillet(pb, objRequest.getDate());
        }
        objRequest = requestRepository.save(objRequest);
        paymentRepository.save(objRequest.getPayment());

        //consertar
        for (RequestItem item : objRequest.getItems()) {
            item.setDiscount(0.0);
            item.setProduct(productService.find(item.getProduct().getId())); //find as product object JSON
            item.setPrice(item.getProduct().getPrice());
            item.setRequest(objRequest);
        }
        requestItemRepository.saveAll(objRequest.getItems());
        //System.out.println(objRequest);
        //emailService.sendOrderConfirmationEmail(objRequest);
        emailService.sendOrderConfirmationHtmlEmail(objRequest);

        return objRequest;
    }

    public Page<Request> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        UserSS user = UserService.authenticated();

        if (user == null) {
            throw new AuthorizationException("Access denied");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        Customer customer =  customerService.find(user.getId());

        return requestRepository.findByCustomer(customer, pageRequest);
    }
}
