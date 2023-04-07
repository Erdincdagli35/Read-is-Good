package com.ed.RIG.service.implementation;

import com.ed.RIG.model.Customer;
import com.ed.RIG.pojo.LoginRequest;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.OrderRepository;
import com.ed.RIG.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class CustomerServiceImp implements CustomerService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Long create(Customer customer) {
        log.info("Customer : create(Customer)");
        customer = customerRepository.save(customer);
        return customer.getId();
    }

    @Override
    public Long addOrder(Long customerId, List<OnlineOrder> orders) {
        log.info("Customer : addOrder(customerId, orders)");
        Customer customer = customerRepository.findOneById(customerId);
        addOrderFromCustomer(customer, orders);
        return customerId;
    }

    private void addOrderFromCustomer(Customer customer, List<OnlineOrder> orders) {
        for (OnlineOrder order : orders){
            order.setCustomer(customer);
            orderRepository.save(order);
        }
    }


    @Override
    public Page<Customer> getAll(Integer pageNo, Integer pageSize) {
        log.info("Customer : getAll()");
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer getById(Long id) {
        log.info("Customer : getById(customerId)");
        return customerRepository.findOneById(id);
    }
}