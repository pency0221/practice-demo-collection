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

package com.enjoy.dubbo.annotation;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.enjoy.action.ServiceConsumer;
import com.enjoy.entity.OrderEntiry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

public class Consumer {

    /**
     * 消费端的内部配置类
     */
    @Configuration
    @EnableDubbo(scanBasePackages = "com.enjoy.action")  //扫包方式 引用远程服务  相当于@Reference的功能
    @ComponentScan(value = {"com.enjoy.action"})
    static class ConsumerConfiguration {
        //分别配置其他三个公共信息 applicationConfig、registryConfig、consumerConfig
        @Bean
        public ApplicationConfig applicationConfig() {
            ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName("busi-consumer");
            return applicationConfig;
        }

        @Bean
        public ConsumerConfig consumerConfig() {
            ConsumerConfig consumerConfig = new ConsumerConfig();
            consumerConfig.setTimeout(3000);
            return consumerConfig;
        }

        @Bean
        public RegistryConfig registryConfig() {
            RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setProtocol("zookeeper");
            registryConfig.setAddress("127.0.0.1");
            registryConfig.setPort(2181);
            return registryConfig;
        }
    }


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        System.out.println("---------dubbo启动成功--------");
        ServiceConsumer serviceConsumer = context.getBean(ServiceConsumer.class);
        //serviceConsumer是本地类  serviceConsumer.getOrderService()获取的OrderService才是代理对象
        OrderEntiry entiry = serviceConsumer.getOrderService().getDetail("1");
        //OrderEntiry entiry = serviceConsumer.getDetail("1");
        System.out.println("result: " + entiry.getMoney());
    }


}
