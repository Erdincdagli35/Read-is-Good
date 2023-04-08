package com.ed.RIG.controller;

import com.ed.RIG.model.Customer;
import com.ed.RIG.service.CustomerService;
import com.ed.RIG.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * I created the Controller for Statistic
 * APIs :
 *   totalValuesByMonth GET
 *
 *  The Controller provides a basic query totalValuesByMonth API.
 * */
@RestController
@RequestMapping("/statistics")
@EnableAutoConfiguration
@CrossOrigin
@AllArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;
    @GetMapping("/totalValuesByMonth")
    public ResponseEntity getAllByTotalValuesByMonth() {
        return ResponseEntity.ok(statisticService.getAllByTotalValuesByMonth());
    }
}
