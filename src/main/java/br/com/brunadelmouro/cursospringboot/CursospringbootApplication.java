package br.com.brunadelmouro.cursospringboot;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursospringbootApplication implements CommandLineRunner { //execute action

	@Autowired
	CategoryRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category category1 = new Category(null, "Informática");
		Category category2 = new Category(null, "Escritório");

		repository.saveAll(Arrays.asList(category1, category2));
	}
}
