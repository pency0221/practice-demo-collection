package com.enjoy.orderService.impl;

import com.enjoy.service.UnknownService;

public class UnknownServiceImpl implements UnknownService{
    @Override
    public String doSomething(String msg) {
        System.out.println("恭喜，你调用成功了");
        return "Hello,"+msg;
    }
}
