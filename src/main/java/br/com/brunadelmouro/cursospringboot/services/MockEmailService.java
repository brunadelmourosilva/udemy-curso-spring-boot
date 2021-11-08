package br.com.brunadelmouro.cursospringboot.services;

import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

import javax.mail.internet.MimeMessage;
import java.util.logging.Logger;

@Log4j2
public class MockEmailService extends AbstractEmailService{

    @Override
    public void sendEmail(SimpleMailMessage msg) {
       log.info("Simulation: sent email");
       log.info(msg.toString());
       log.info("Email email sent!");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        log.info("Simulation: sent HTML email");
        log.info(msg.toString());
        log.info("Email email sent!");
    }
}
