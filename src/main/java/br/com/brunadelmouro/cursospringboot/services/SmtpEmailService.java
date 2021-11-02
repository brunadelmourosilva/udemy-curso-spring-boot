package br.com.brunadelmouro.cursospringboot.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

@Log4j2
public class SmtpEmailService extends AbstractEmailService{

    @Autowired
    MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulation: sent email");
        mailSender.send(msg);
        log.info("Email email sent!");
    }
}
