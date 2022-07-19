package br.com.brunadelmouro.cursospringboot.services;


import br.com.brunadelmouro.cursospringboot.domain.State;
import br.com.brunadelmouro.cursospringboot.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateService {

    StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public List<State> findAll(){
        return stateRepository.findAllByOrderByName();
    }
}
