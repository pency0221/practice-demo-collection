package com.enjoy.orderService.impl;

import com.enjoy.service.PayService;

public class PayServiceImpl implements PayService {
    @Override
    public String pay(long money) {

        try {
            System.out.println("PayService.pay耗时2秒");
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(money);
    }
}
