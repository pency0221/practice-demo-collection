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
import com.enjoy.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        ctx.start();

        System.out.println("---------spring启动成功--------");

//        OrderService orderService = (OrderService) ctx.getBean("orderService");
//        OrderEntiry entiry = orderService.getDetail("1");
//        System.out.println("测试orderService.getDetail调用功能，调用结果：" + JSON.toJSONString(entiry));

//        InfoService infoService = (InfoService) ctx.getBean("infoService");
//        Object o = infoService.sayHello("james");
//        System.out.println("测试infoService.getInfo调用功能，调用结果：" + JSON.toJSONString(o));
//
        Map<String,String> info = new HashMap();
        info.put("target","orderService");
        info.put("methodName","getDetail");
        info.put("arg","1");
//        Object result = infoService.passInfo(info);
//        System.out.println("测试远程调用功能，调用结果：" + JSON.toJSONString(result));

//        System.in.read();
    }

    @Configuration
    static class ConsumerConfiguration {

        @Bean
        public InfoService infoService() throws RemoteException, MalformedURLException, AlreadyBoundException {
            InfoService infoService = null;

            try {
                //取远程服务实现
                infoService = (InfoService) Naming.lookup(InfoService.RMI_URL);
            } catch (NotBoundException e) {
                e.printStackTrace();
            }

            return infoService;
        }
    }

}
