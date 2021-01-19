package com.enjoy.orderService.impl;

import com.enjoy.entity.UserEntiry;
import com.enjoy.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public UserEntiry getDetail(String id) {
        try {
            System.out.println("UserService.getDetail耗时2秒");
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserEntiry user = new UserEntiry();
        user.setId("U001");
        user.setName("李四");
        user.setAddress("广州");
        user.setBalance(5000);
        return user;
    }


}
