package com.ed.RIG.service;

import com.ed.RIG.model.OnlineOrder;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public interface OrderService {

    Page<OnlineOrder> getAll(Integer pageNo, Integer pageSize, Long customerId);

    OnlineOrder getById(Long id);

    Long create(OnlineOrder order, List<Long> bookIds, List<Integer> pieces);

    Page<OnlineOrder> getAllByStartDateBetweenEndDate(Integer pageNo, Integer pageSize, Instant startDate, Instant endDate);
}
