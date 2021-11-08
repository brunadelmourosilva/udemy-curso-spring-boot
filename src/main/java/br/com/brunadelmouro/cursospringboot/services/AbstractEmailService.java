package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.Request;
import javassist.Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

//pattern: Template Method
public abstract class AbstractEmailService implements EmailService{

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    protected String htmlFromTemplateRequest(Request obj){
        //thymeleaf
        Context context = new Context();
        context.setVariable("request", obj); //nickname for thymeleaf

        return templateEngine.process("email/requestConfirm", context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Request obj){

        try {
            MimeMessage mm = prepareMimeMessageFromRequest(obj);
            sendHtmlEmail(mm);
        }
        catch (MessagingException e){
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromRequest(Request obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

        mmh.setTo(obj.getCustomer().getEmail());//recipient
        mmh.setFrom(sender);//sender

        mmh.setSubject("Order confirmed! Code: " + obj.getId());//title
        mmh.setSentDate(new Date(System.currentTimeMillis()));//date
        mmh.setText(htmlFromTemplateRequest(obj), true);//e-mail

        return  mimeMessage;
    }

}
