package com.enjoy.dubbo.xml;

import com.alibaba.fastjson.JSON;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/dubbo-server.xml");

        ctx.start();
        System.out.println("---------dubbo启动成功--------");

        // 保证服务一直开着
        synchronized (XmlServer.class) {
            try {
                XmlServer.class.wait();
            } catch (Throwable e) {
            }
        }

    }
}
