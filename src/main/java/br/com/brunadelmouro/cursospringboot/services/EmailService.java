package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Request;
import org.springframework.mail.SimpleMailMessage;

//contract
public interface EmailService {

    void sendOrderConfirmationEmail(Request obj);

    void sendEmail(SimpleMailMessage msg);
}
