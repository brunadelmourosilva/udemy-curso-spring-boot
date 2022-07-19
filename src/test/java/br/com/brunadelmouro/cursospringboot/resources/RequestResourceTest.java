package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.CursospringbootApplication;
import br.com.brunadelmouro.cursospringboot.domain.*;
import br.com.brunadelmouro.cursospringboot.domain.enums.CustomerType;
import br.com.brunadelmouro.cursospringboot.repositories.PaymentRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestItemRepository;
import br.com.brunadelmouro.cursospringboot.repositories.RequestRepository;
import br.com.brunadelmouro.cursospringboot.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CursospringbootApplication.class)
@ActiveProfiles("test")
class RequestResourceTest {

    private static final String REQUEST_URL = "/requests";

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Autowired
    RequestResource requestResource;

    @Autowired
    RequestService requestService;

    @Autowired
    BilletService billetService;

    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmailService emailService;

    @MockBean
    PaymentRepository paymentRepository;

    @MockBean
    RequestRepository requestRepository;

    @MockBean
    RequestItemRepository requestItemRepository;

    Request request;
    Customer customer;
    Address address;
    City city;
    State state;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(requestResource).build();
        objectMapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date(sdf.format(System.currentTimeMillis()));

        //mock's
        customer = new Customer(1, "Bruna", "brunadelmouro@gmail.com", "00099911189", CustomerType.PESSOAFISICA, "123");
        state = new State(1, "Minas Gerais");
        city = new City(1, "Itajubá", state);
        address = new Address(1, "Av. 1", "300", "1B", "Pinheirinho", "37500-190", customer, city);
        request = new Request(1, date, customer, address);

        //creating a new instance for the classes
        requestResource = new RequestResource(requestService);
        requestService = new RequestService(billetService, productService, customerService, paymentRepository, requestRepository, requestItemRepository, emailService);
    }

    //TODO fix exception
    @Test
    void findOrderByIdWithSuccessTest() throws Exception{
        given(requestRepository.findById(request.getId())).willReturn(Optional.of(request));

        mockMvc
                .perform(MockMvcRequestBuilders.get(REQUEST_URL.concat("1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void insert() {
    }

    @Test
    void findPage() {
    }
}