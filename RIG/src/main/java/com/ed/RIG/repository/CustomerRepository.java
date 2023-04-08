package com.ed.RIG.repository;

import com.ed.RIG.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findOneById(Long id);
    Page<Customer> findAll(Pageable pageable);

    Customer findByUserName(String username);
}
