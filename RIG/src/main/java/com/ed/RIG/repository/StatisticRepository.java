package com.ed.RIG.repository;

import com.ed.RIG.model.Customer;
import com.ed.RIG.pojo.MonthlyValues;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends CrudRepository<Customer, Long> {
    @Query("SELECT DISTINCT o.month as month, count(o.id) as totalOrderCount, " +
            "count(b.id) as totalBookCount, sum(b.price * b.piece) as totalPurchasedAmount " +
            "FROM OnlineOrder o JOIN o.books b " +
            "GROUP BY o.month")
    List<MonthlyValues> findAllByOrderAndBook();
}
