package com.ed.RIG.validation;

import com.ed.RIG.model.Customer;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerValidation {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    public boolean existsCustomer(Customer customer) {
        return customerRepository.findOneById(customer.getId()) == null;
    }

    public boolean existsCustomerById(Long id) {
        return customerRepository.findOneById(id) != null;
    }

    public boolean existsCustomerRelatedWithOrder(Long id){
        return orderRepository.findAllByCustomer_Id(id) != null;
    }
}
