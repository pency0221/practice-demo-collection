package com.enjoy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.enjoy.entity.OrderEntiry;
import org.springframework.beans.factory.annotation.Autowired;

@Service  //dubbo的@Service注解
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderEntiry getDetail(String id) {
        System.out.println(super.getClass().getName()+"被调用一次："+System.currentTimeMillis());
        OrderEntiry orderEntiry = new OrderEntiry();
        orderEntiry.setId("O0001");
        orderEntiry.setMoney(1000);
        orderEntiry.setUserId("U0001");
        return orderEntiry;
    }
}
