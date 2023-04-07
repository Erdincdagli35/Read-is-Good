package com.ed.RIG.repository;

import com.ed.RIG.model.OnlineOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface OrderRepository extends CrudRepository<OnlineOrder, Long> {
    OnlineOrder findOneById(Long id);

    Page<OnlineOrder> findAll(Pageable pageable);
    Page<OnlineOrder> findAllByCustomer_Id(Pageable pageable,Long customerId);

    List<OnlineOrder> findAllByCustomer_Id(Long customerId);

    @Query("Select o From OnlineOrder o " +
            "Where o.startDate <= ?1 and o.endDate >= ?2 " +
            "Order By o.id")
    Page<OnlineOrder> findAllByStartDateBetweenEndTime(Instant startDate, Instant endDate,Pageable pageable);
}
