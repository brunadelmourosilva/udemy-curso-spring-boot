package br.com.brunadelmouro.cursospringboot.services;

import br.com.brunadelmouro.cursospringboot.domain.*;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.domain.enums.StatusPayment;
import br.com.brunadelmouro.cursospringboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    RequestItemRepository requestItemRepository;

    public void instantiateTestDatabase() throws ParseException {
        Category category1 = new Category(null, "Informática");
        Category category2 = new Category(null, "Escritório");
        Category category3 = new Category(null, "Cama, mesa e banho");
        Category category4 = new Category(null, "Eletrônicos");
        Category category5 = new Category(null, "Jardinagem");
        Category category6 = new Category(null, "Decoração");
        Category category7 = new Category(null, "Perfumaria");
        Category category8 = new Category(null, "Teste");

        Product product1 = new Product(null, "Computador", 2000.00);
        Product product2 = new Product(null, "Impressora", 800.00);
        Product product3 = new Product(null, "Mouse", 80.00);
        Product product4 = new Product(null, "Mesa de escritório", 300.00);
        Product product5 = new Product(null, "Toalha", 50.00);
        Product product6 = new Product(null, "Colcha", 200.00);
        Product product7 = new Product(null, "TV true color", 1200.00);
        Product product8 = new Product(null, "Roçadeira", 800.00);
        Product product9 = new Product(null, "Abajour", 100.00);
        Product product10 = new Product(null, "Pendente", 180.00);
        Product product11 = new Product(null, "Shampoo", 90.00);

        //categories - products
        category1.getProducts().addAll(Arrays.asList(product1, product2, product3));
        category2.getProducts().addAll(Arrays.asList(product2, product4));
        category3.getProducts().addAll(Arrays.asList(product5, product6));
        category4.getProducts().addAll(Arrays.asList(product1, product2, product7));
        category5.getProducts().addAll(Arrays.asList(product8));
        category6.getProducts().addAll(Arrays.asList(product9, product10));
        category7.getProducts().addAll(Arrays.asList(product11));

        //products - categories
        product1.getCategories().addAll(Arrays.asList(category1, category4));
        product2.getCategories().addAll(Arrays.asList(category1, category2, category4));
        product3.getCategories().addAll(Arrays.asList(category1, category4));
        product4.getCategories().addAll(Arrays.asList(category2));
        product5.getCategories().addAll(Arrays.asList(category3));
        product6.getCategories().addAll(Arrays.asList(category3));
        product7.getCategories().addAll(Arrays.asList(category4));
        product8.getCategories().addAll(Arrays.asList(category5));
        product9.getCategories().addAll(Arrays.asList(category6));
        product10.getCategories().addAll(Arrays.asList(category6));
        product11.getCategories().addAll(Arrays.asList(category7));


        //repository - database
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3, category4, category5, category6, category7));
        productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10, product11));


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


        Customer customer1 = new Customer(null, "Maria Silva", "brunadelmouro@gmail.com", "36378912377", CustomerType.PESSOAFISICA, pe.encode("123"));

        customer1.getPhones().addAll(Arrays.asList("27363323", "93838393"));

        //constructor - association many to one
        Address address1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", customer1, city1);
        Address address2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", customer1, city2);

        //customer - addresses
        customer1.getAddresses().addAll(Arrays.asList(address1, address2));

        //repository - database
        customerRepository.saveAll(Arrays.asList(customer1));
        addressRepository.saveAll(Arrays.asList(address1, address2));


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        Request request1 = new Request(null, sdf.parse("30/09/2017 10:32"), customer1, address1);
        Request request2 = new Request(null, sdf.parse("10/10/2017 19:35"), customer1, address2);

        Payment payment1 = new PaymentCard(null, StatusPayment.QUITADO, request1, 6);
        request1.setPayment(payment1);

        Payment payment2 = new PaymentBillet(null, StatusPayment.PENDENTE, request2, sdf.parse("20/10/2017 00:00"), null);
        request2.setPayment(payment2);

        customer1.getRequests().addAll(Arrays.asList(request1, request2));


        //repository - database
        requestRepository.saveAll(Arrays.asList(request1, request2));
        paymentRepository.saveAll(Arrays.asList(payment1, payment2));


        RequestItem requestItem1 = new RequestItem(request1, product1, 0.00, 1, 2000.00);
        RequestItem requestItem2 = new RequestItem(request1, product3, 0.00, 2, 80.00);
        RequestItem requestItem3 = new RequestItem(request2, product2, 100.00, 1, 800.00);
        RequestItem requestItem4 = new RequestItem(request2, product5, 0.00, 2, 60.00);

        request1.getItems().addAll(Arrays.asList(requestItem1, requestItem2));
        request2.getItems().addAll(Arrays.asList(requestItem3, requestItem4));

        product1.getItems().addAll(Arrays.asList(requestItem1));
        product2.getItems().addAll(Arrays.asList(requestItem3));
        product3.getItems().addAll(Arrays.asList(requestItem2));
        product5.getItems().addAll(Arrays.asList(requestItem4));

        //repository - database
        requestItemRepository.saveAll(Arrays.asList(requestItem1, requestItem2, requestItem3, requestItem4));
    }
}
