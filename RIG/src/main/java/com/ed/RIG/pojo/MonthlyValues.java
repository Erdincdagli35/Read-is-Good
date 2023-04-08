package com.ed.RIG.pojo;

import com.ed.RIG.constant.Month;

public interface MonthlyValues {
    Month getMonth();
    String getTotalOrderCount();
    String getTotalBookCount();
    String getTotalPurchasedAmount();
}
