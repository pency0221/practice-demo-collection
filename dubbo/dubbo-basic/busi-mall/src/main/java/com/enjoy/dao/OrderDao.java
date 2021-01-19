package com.enjoy.dao;

import com.enjoy.entity.OrderEntiry;

public interface OrderDao {
    OrderEntiry getDetail(String id);
    OrderEntiry submit(OrderEntiry order);
    boolean cancel(OrderEntiry order);
}
