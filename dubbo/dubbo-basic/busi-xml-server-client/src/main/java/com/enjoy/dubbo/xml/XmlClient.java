package com.enjoy.dubbo.xml;

import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/dubbo-client.xml");
        ctx.start();

        System.out.println("---------dubbo启动成功--------");
        OrderService orderService = (OrderService) ctx.getBean("orderService"); // get remote service proxy
        OrderEntiry entiry = orderService.getDetail("1");
        System.out.println("echo result: " + entiry.getMoney());
    }
}
