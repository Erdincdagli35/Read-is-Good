package com.ed.RIG.controller;

import com.ed.RIG.config.JwtService;
import com.ed.RIG.model.Customer;
import com.ed.RIG.model.OnlineOrder;
import com.ed.RIG.pojo.CustomerLoginRequest;
import com.ed.RIG.service.CustomerService;
import com.ed.RIG.validation.CustomerValidation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/*
 * I created the Controller for Customer
 * APIs :
 *   create POST
 *   addOrder POST
 *   getAll GET
 *   getById GET
 *
 * The Controller provides a basic create, getAll, getById API.
 * Also, I created a addOrder API for Customer - OnlineOrder Entity Relationship
 * */
@RestController
@RequestMapping("/customers")
@EnableAutoConfiguration
@CrossOrigin
@AllArgsConstructor
@NoArgsConstructor
public class CustomerController {

    @Autowired
   CustomerService customerService;

    @Autowired
    CustomerValidation customerValidation;

    @Autowired
    private JwtService jwtService;

    /*•	Registering New Customer */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerLoginRequest customerLoginRequest) throws IOException {
        Customer customer = customerService.findByUsername(customerLoginRequest.getUsername());

        if (customer == null || !customer.getPassword().equals(customerLoginRequest.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Username or password is incorrect.");
        }

        String token = jwtService.generateToken(customer.getUserName());
        return ResponseEntity.ok("Barrier " + token);
    }

    /*Placing a new order*/
    //I create a customer as basic. There is a basic existsCustomer validation for creating with same id.
    //The API returns the id as response
    @PostMapping
    public ResponseEntity create(@RequestBody Customer customer) {
        if (!customerValidation.existsCustomer(customer)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is a customer with same id:" + customer.getId());
        }

        return ResponseEntity.ok(customerService.create(customer));
    }

    //I add order on customer entity. There are two validation for checking existCustomer and existsCustomerRelatedWithOrder.
    //The API returns customerId
    @PostMapping("/{customerId}/addOrder")
    public ResponseEntity addOrder(@PathVariable Long customerId,
                                   @RequestBody List<OnlineOrder> orders) {
        if (!customerValidation.existsCustomerById(customerId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a Customer id : " + customerId);
        }
        if (!customerValidation.existsCustomerRelatedWithOrder(customerId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a related order for this Customer id : " + customerId);
        }

        return ResponseEntity.ok(customerService.addOrder(customerId, orders));
    }

    //I list all customer this API as pageable. There are two parameters for pageable.
    //The parameters are mandatory. The API returns all customers data as pageable
    //Please use the 1-10 for pageNo. It's starting 1.
    @GetMapping
    public ResponseEntity<Page<Customer>> getAll(@RequestParam(value = "pageNo") Integer pageNo,
                                                 @RequestParam(value = "pageSize") Integer pageSize) {
        return ResponseEntity.ok(customerService.getAll(pageNo, pageSize));
    }

    /*Viewing the order details*/
    //I list a customer by customer id. The API returns a customer.
    //There is a basic existCustomer validation.
    @GetMapping("/{customerId}")
    public ResponseEntity getById(@PathVariable Long customerId) {
        if (!customerValidation.existsCustomerById(customerId)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("There is not a Customer id : " + customerId);
        }

        return ResponseEntity.ok(customerService.getById(customerId));
    }
}
