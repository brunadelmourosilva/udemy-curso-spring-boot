package br.com.brunadelmouro.cursospringboot.resources;

import br.com.brunadelmouro.cursospringboot.CursospringbootApplication;
import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.State;
import br.com.brunadelmouro.cursospringboot.dto.CityDTO;
import br.com.brunadelmouro.cursospringboot.repositories.CityRepository;
import br.com.brunadelmouro.cursospringboot.repositories.StateRepository;
import br.com.brunadelmouro.cursospringboot.services.CityService;
import br.com.brunadelmouro.cursospringboot.services.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CursospringbootApplication.class)
@ActiveProfiles(value = "test")
class StateResourceTest {

    private static final String STATE_URL = "/states";

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Autowired
    StateResource stateResource;

    @Autowired
    StateService stateService;

    @Autowired
    CityService cityService;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    StateRepository stateRepository;

    CityDTO cityDTO;

    City city;

    State state;

    List<CityDTO> citiesDto;
    List<City> cities;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stateResource).build();
        objectMapper = new ObjectMapper();

        state = new State(1, "Minas Gerais");
        city = new City(1, "Itajubá", state);
        cityDTO = new CityDTO(1, "Itajubá");

        citiesDto = List.of(cityDTO);
        cities = List.of(city);

        stateResource = new StateResource(stateService, cityService);

        stateService = new StateService(stateRepository);
        cityService = new CityService(cityRepository);
    }

    @Test
    void findCitiesWithSuccessTest() throws Exception {
        given(cityRepository.findCities(state.getId())).willReturn(cities);

        mockMvc
                .perform(MockMvcRequestBuilders.get(STATE_URL.concat("/1/cities"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citiesDto)))
                .andExpect(status().isOk());
    }
}