package br.com.brunadelmouro.cursospringboot.repositories;

import br.com.brunadelmouro.cursospringboot.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//layer - repository

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> { //class, identifier attribute type

    @Transactional(readOnly=true)
    Customer findByEmail(String email);
}
