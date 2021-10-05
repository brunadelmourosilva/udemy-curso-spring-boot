package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.*;
import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;
import br.com.brunadelmouro.cursospringboot.repositories.PaymentRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestItemRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestRepository;
import br.com.brunadelmouro.cursospringboot.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    BilletService billetService;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    ProductService productService;

    @Autowired
    RequestItemRepository requestItemRepository;


    public Request find(Integer id){
        Optional<Request> obj = requestRepository.findById(id);

        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Object not found! Id: " + id + ", Type: " + Request.class.getName())
        );
    }

    @Transactional
    public Request insert(Request objRequest){
        objRequest.setId(null);
        objRequest.setDate(new Date());

        objRequest.getPayment().setStatus(StatusPayment.PENDENTE);

        objRequest.getPayment().setRequest(objRequest);

        if(objRequest.getPayment() instanceof PaymentBillet){
            PaymentBillet pb = (PaymentBillet) objRequest.getPayment();
            billetService.fillPaymentWithBillet(pb, objRequest.getDate());
        }
        objRequest = requestRepository.save(objRequest);
        paymentRepository.save(objRequest.getPayment());

        for (RequestItem item : objRequest.getItems()) {
            item.setDiscount(0.0);
            item.setPrice(productService.find(item.getProduct().getId()).getPrice());
            item.setRequest(objRequest);
        }
        requestItemRepository.saveAll(objRequest.getItems());
        return objRequest;
    }
}
