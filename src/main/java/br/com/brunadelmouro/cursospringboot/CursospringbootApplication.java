package br.com.brunadelmouro.cursospringboot;

import br.com.brunadelmouro.cursospringboot.domain.Category;
import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.Product;
import br.com.brunadelmouro.cursospringboot.domain.State;
import br.com.brunadelmouro.cursospringboot.repositories.CategoryRepository;
import br.com.brunadelmouro.cursospringboot.repositories.CityRepository;
import br.com.brunadelmouro.cursospringboot.repositories.ProductRepository;
import br.com.brunadelmouro.cursospringboot.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursospringbootApplication implements CommandLineRunner { //execute action

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	StateRepository stateRepository;
	@Autowired
	CityRepository cityRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Category category1 = new Category(null, "Informática");
		Category category2 = new Category(null, "Escritório");

		Product product1 = new Product(null, "Computador", 2000.00);
		Product product2 = new Product(null, "Impressora", 800.00);
		Product product3 = new Product(null, "Mouse", 80.00);

		//categories - products
		category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
		category2.getProducts().addAll(Arrays.asList(product2));

		//products - categories
		product1.getCategories().addAll(Arrays.asList(category1));
		product2.getCategories().addAll(Arrays.asList(category1, category2));
		product3.getCategories().addAll(Arrays.asList(category1));

		//repository - database
		categoryRepository.saveAll(Arrays.asList(category1, category2));
		productRepository.saveAll(Arrays.asList(product1, product2, product3));


		State state1 = new State(null, "Minas Gerais");
		State state2 = new State(null, "São Paulo");

		//constructor - association many to one
		City city1 = new City(null, "Itajubá", state1);
		City city2 = new City(null, "São Paulo", state2);
		City city3 = new City(null, "Campinas", state2);

		//state - cities
		state1.getCities().addAll(Arrays.asList(city1));
		state2.getCities().addAll(Arrays.asList(city2, city3));

		//repository - database
		stateRepository.saveAll(Arrays.asList(state1, state2));
		cityRepository.saveAll(Arrays.asList(city1, city2, city3));
	}
}
