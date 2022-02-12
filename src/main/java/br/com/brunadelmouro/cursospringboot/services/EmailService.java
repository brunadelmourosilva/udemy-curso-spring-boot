package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.Request;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

//contract
public interface EmailService {

    void sendOrderConfirmationEmail(Request obj);

    void sendEmail(SimpleMailMessage msg);

    //HTML E-mail
    void sendOrderConfirmationHtmlEmail(Request obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Customer customer, String newPass);
}
