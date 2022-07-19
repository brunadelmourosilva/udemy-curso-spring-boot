package br.com.brunadelmouro.cursospringboot.config;

import br.com.brunadelmouro.cursospringboot.services.DBService;
import br.com.brunadelmouro.cursospringboot.services.EmailService;
import br.com.brunadelmouro.cursospringboot.services.MockEmailService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.text.ParseException;

@Configuration
@Profile("test") //bean active
public class TestConfig {

    private DBService dbService;

    public TestConfig(DBService dbService){
        this.dbService = dbService;
    }

    /*
    @Bean
    public boolean instantiateDatabase() throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }
     */

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }

    @Bean
    public JavaMailSenderImpl javaMailSenderImpl(){
        return new JavaMailSenderImpl();
    }
}
