package br.com.brunadelmouro.cursospringboot.repositories;

import br.com.brunadelmouro.cursospringboot.domain.City;
import br.com.brunadelmouro.cursospringboot.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//layer - repository

@Repository
public interface CityRepository extends JpaRepository<City, Integer> { //class, identifier attribute type

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM City obj WHERE obj.state.id = :stateId ORDER BY obj.name")
    public List<City> findCities(@Param("stateId") Integer state_id);
}
