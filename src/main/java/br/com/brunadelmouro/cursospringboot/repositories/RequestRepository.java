package br.com.brunadelmouro.cursospringboot.repositories;

import br.com.brunadelmouro.cursospringboot.domain.Address;
import br.com.brunadelmouro.cursospringboot.domain.Customer;
import br.com.brunadelmouro.cursospringboot.domain.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//layer - repository

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> { //class, identifier attribute type

    //ERRO
    @Transactional(readOnly=true)
    Page<Request> findByCustomer(Customer customer, Pageable pageRequest);


}

