package br.com.brunadelmouro.cursospringboot.repositories;

import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//layer - repository

@Repository
public interface CityRepository extends JpaRepository<City, Integer> { //class, identifier attribute type

}
