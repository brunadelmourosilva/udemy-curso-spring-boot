package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Request;
import javassist.Loader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

//pattern: Template Method
public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Request obj){
        SimpleMailMessage sm = prepareSimpleMailMessageFromRequest(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromRequest(Request obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCustomer().getEmail());//recipient
        sm.setFrom(sender);//sender

        sm.setSubject("Order confirmed! Code: " + obj.getId());//title
        sm.setSentDate(new Date(System.currentTimeMillis()));//date
        sm.setText(obj.toString());//e-mail

        return sm;
    }
}
