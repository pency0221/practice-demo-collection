package com.enjoy.infoService.impl;



import com.enjoy.service.InfoService;

public class InfoServiceBImpl implements InfoService {

    @Override
    public Object sayHello(String name) {
        System.out.println(name+",你好，调通了B实现！");
        return name+",你好，调通了B实现！";
    }



}
