package com.enjoy.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrderServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:dubbo.xml");

        ctx.start();
        System.out.println("---------dubbo启动成功--------");

        // 保证服务一直开着
        synchronized (OrderServer.class) {
            try {
                OrderServer.class.wait();
            } catch (Throwable e) {
            }
        }

    }
}
