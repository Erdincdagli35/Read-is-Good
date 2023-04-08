package com.ed.RIG.service.implementation;

import com.ed.RIG.model.Customer;
import com.ed.RIG.pojo.MonthlyValues;
import com.ed.RIG.repository.CustomerRepository;
import com.ed.RIG.repository.StatisticRepository;
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
    private final StatisticRepository statisticRepository;

    @Override
    public List<MonthlyValues> getAllByTotalValuesByMonth() {
        List<MonthlyValues> customers = statisticRepository.findAllByOrderAndBook();
        return customers;
    }
}