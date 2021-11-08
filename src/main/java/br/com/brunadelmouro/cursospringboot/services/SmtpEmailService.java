package br.com.brunadelmouro.cursospringboot.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

@Log4j2
public class SmtpEmailService extends AbstractEmailService{

    @Autowired
    MailSender mailSender;

    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Simulation: sent email");
        mailSender.send(msg);
        log.info("Email email sent!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulation: sent email");
        javaMailSender.send(msg);
        log.info("Email email sent!");
    }
}
