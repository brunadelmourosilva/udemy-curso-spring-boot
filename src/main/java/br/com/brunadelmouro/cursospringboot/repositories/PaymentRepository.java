package br.com.brunadelmouro.cursospringboot.repositories;

import br.com.brunadelmouro.cursospringboot.domain.Address;
import br.com.brunadelmouro.cursospringboot.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//layer - repository

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> { //class, identifier attribute type

}
