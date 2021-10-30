package br.com.brunadelmouro.cursospringboot.services;

import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

import java.util.logging.Logger;

@Log4j2
public class MockEmailService extends AbstractEmailService{

    @Override
    public void sendEmail(SimpleMailMessage msg) {
       log.info("Simulation: sent email");
       log.info(msg.toString());
       log.info("Email email sent!");
    }
}
