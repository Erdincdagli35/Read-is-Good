package com.ed.RIG.service;

import com.ed.RIG.pojo.MonthlyValues;

import java.util.List;

public interface StatisticService {
    List<MonthlyValues> getAllByTotalValuesByMonth();
}
