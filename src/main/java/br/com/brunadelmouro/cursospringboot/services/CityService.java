package br.com.brunadelmouro.cursospringboot.services;


import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public List<City> findByState(Integer stateId){
        return cityRepository.findCities(stateId);
    }
}
