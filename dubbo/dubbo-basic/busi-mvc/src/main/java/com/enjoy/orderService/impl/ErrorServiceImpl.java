package com.enjoy.orderService.impl;

import com.enjoy.service.ErrorService;

public class ErrorServiceImpl implements ErrorService {
    @Override
    public String doSomeThing(String msg) {
        if (System.currentTimeMillis()%3 == 0){
            throw new RuntimeException("sorry, something error！");
        }
        return "success";
    }
}
