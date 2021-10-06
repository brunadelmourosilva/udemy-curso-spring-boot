package br.com.brunadelmouro.cursospringboot;

import br.com.brunadelmouro.cursospringboot.domain.*;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;
import br.com.brunadelmouro.cursospringboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursospringbootApplication implements CommandLineRunner { //execute action

	public static void main(String[] args) {
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


	}
}
