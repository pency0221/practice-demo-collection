/*
 *
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.enjoy;

import com.alibaba.fastjson.JSON;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.InfoService;
import com.enjoy.service.InfoServiceImpl;
import com.enjoy.service.OrderService;
import com.enjoy.service.OrderServiceImpl;
import com.enjoy.utils.InvokeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

public class Provider {

    @Configuration
    static class ProviderConfiguration {
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        ctx.start();

        System.out.println("---------spring启动成功--------");

        OrderService orderService = (OrderService) ctx.getBean("orderService");
        OrderEntiry entiry = orderService.getDetail("1");
        System.out.println("测试orderService.getDetail调用功能，调用结果：" + JSON.toJSONString(entiry));

        System.out.println("----------------------------");
        Map<String,String> info = new HashMap();
        info.put("target","orderService");
        info.put("methodName","getDetail");
        info.put("arg","1");
        Object result = InvokeUtils.call(info,ctx);
        System.out.println("测试InvokeUtils.call调用功能，调用结果：" + JSON.toJSONString(result));

//        initProtocol(ctx);
        initProtocol2(ctx);
        System.in.read();
    }

    //服务暴露
    public static void initProtocol(ApplicationContext ctx) throws RemoteException, AlreadyBoundException, MalformedURLException {
        InfoService infoService = new InfoServiceImpl();
        //注冊通讯端口
        LocateRegistry.createRegistry(InfoService.port);
        //注冊通讯路径
        Naming.bind(InfoService.RMI_URL, infoService);
        System.out.println("初始化rmi绑定");
    }

    public static void initProtocol2(final ApplicationContext ctx) throws RemoteException, AlreadyBoundException, MalformedURLException {
        InfoService infoService = new InfoServiceImpl(){
            public Object passInfo(Map<String, String> info) {//对象，方法，参数

                super.passInfo(info);//info内包含的信息，是反射需要的信息
                Object result = InvokeUtils.call(info,ctx);//反射调用的工具类
                System.out.println("测试InvokeUtils.call调用功能，调用结果：" + JSON.toJSONString(result));
                return result;
            }
        };

        //注冊通讯端口
        LocateRegistry.createRegistry(InfoService.port);
        //注冊通讯路径
        Naming.bind(InfoService.RMI_URL, infoService);
        System.out.println("初始化rmi绑定");
    }



}
