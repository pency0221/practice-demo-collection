package com.enjoy.orderService.impl;

import com.enjoy.dao.OrderDao;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import com.enjoy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public OrderEntiry getDetail(String id) {
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        OrderEntiry orderEntiry =  orderDao.getDetail(id);
//        try {
//            Thread.sleep(2000);//休眼2s，测试超时功能
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return orderEntiry;
    }
}
