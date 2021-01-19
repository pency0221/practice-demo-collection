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

package com.enjoy.action;

import com.alibaba.dubbo.config.annotation.Reference;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.OrderService;
import org.springframework.stereotype.Component;

@Component("annotatedConsumer")
public class ServiceConsumer {

    @Reference //dubbo的
    private OrderService orderService;

   /* 可以引用 注册多个接口 多个远程代理服务
   @Reference
    private XxxService xxxService;*/

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }




   public OrderEntiry getDetail(String id){
      return  orderService.getDetail(id);
   };

}
