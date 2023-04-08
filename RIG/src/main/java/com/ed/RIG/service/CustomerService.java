package com.ed.RIG.service;

import com.ed.RIG.model.Customer;
import com.ed.RIG.model.OnlineOrder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Page<Customer> getAll(Integer pageNo, Integer pageSize);

    Customer getById(Long id);

    Long create(Customer customer);

    Long addOrder(Long customerId, List<OnlineOrder> orders);

    Customer findByUsername(String username);
}
