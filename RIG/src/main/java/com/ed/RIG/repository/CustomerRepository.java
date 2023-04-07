package com.ed.RIG.repository;

import com.ed.RIG.model.Customer;
import com.ed.RIG.pojo.MonthlyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findOneById(Long id);
    Page<Customer> findAll(Pageable pageable);

    @Query("SELECT DISTINCT c FROM Customer c " +
            "JOIN c.onlineOrders o " +
            "JOIN o.books b " +
            "WHERE b.id IN (SELECT b.id FROM Book b)")
    List<Customer> findAllByOrderAndBook();
}
