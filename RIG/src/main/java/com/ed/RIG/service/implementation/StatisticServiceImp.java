package com.ed.RIG.service.implementation;

import com.ed.RIG.model.Customer;
import com.ed.RIG.pojo.MonthlyValues;
import com.ed.RIG.repository.BookRepository;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.OrderRepository;
import com.ed.RIG.service.StatisticService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class StatisticServiceImp implements StatisticService {
    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> totalValuesByMonth() {

        return customerRepository.findAllByOrderAndBook();
    }
}