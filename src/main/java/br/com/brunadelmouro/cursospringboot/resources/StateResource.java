package br.com.brunadelmouro.cursospringboot.resources;


import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.State;
import br.com.brunadelmouro.cursospringboot.dto.CityDTO;
import br.com.brunadelmouro.cursospringboot.dto.StateDTO;
import br.com.brunadelmouro.cursospringboot.services.CityService;
import br.com.brunadelmouro.cursospringboot.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/states")
public class StateResource {

    StateService stateService;
    CityService cityService;

    public StateResource(StateService stateService, CityService cityService) {
        this.stateService = stateService;
        this.cityService = cityService;
    }

    @RequestMapping(method= RequestMethod.GET)
    public ResponseEntity<List<StateDTO>> findAll() {
        List<State> list = stateService.findAll();

        List<StateDTO> listDto = list.stream().map(obj -> new StateDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value="/{stateId}/cities", method=RequestMethod.GET)
    public ResponseEntity<List<CityDTO>> findCities(@PathVariable Integer stateId) {
        List<City> list = cityService.findByState(stateId);

        //convert a list of city entity to city of DTO type
        List<CityDTO> listDto = list.stream().map(obj -> new CityDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }
}
