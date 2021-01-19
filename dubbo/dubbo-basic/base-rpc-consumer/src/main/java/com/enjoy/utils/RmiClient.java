package com.enjoy.utils;

import com.alibaba.fastjson.JSON;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.service.InfoService;
import com.enjoy.service.OrderService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class RmiClient {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        InfoService infoService = null;

        try {
            //取远程服务实现
            infoService = (InfoService) Naming.lookup(InfoService.RMI_URL);
            Object ret = infoService.sayHello("james");
            System.out.println("测试远程调用功能infoService.sayHello，调用结果：" + JSON.toJSONString(ret));

            //呼叫远程反射方法
            Map<String,String> info = new HashMap();
            info.put("target","orderService");
            info.put("methodName","getDetail");
            info.put("arg","1");
            Object result = infoService.passInfo(info);
            System.out.println("测试远程调用功能，调用结果：" + JSON.toJSONString(result));

            //静态代理方法  //pency 也就是把反射信息移入到了方法里包装 返回类型也都对应确定写死 这样业务开发人员在此直接调用感受不到包装info信息 其实是被静态代理调用了
            OrderService service = getService(infoService);
            Object result2 = service.getDetail("1");//透明化调用，不增加开发人员的负担
            System.out.println("测试远程调用功能，调用结果：" + JSON.toJSONString(result2));
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * 静态代理,dubbo中是动态编译类来实现
     */
    public static OrderService getService(final InfoService infoService){
        OrderService service = new OrderService(){
            @Override
            public OrderEntiry getDetail(String id) {
                Map<String,String> info = new HashMap();
                //写死了反射的目标，静态代理
                info.put("target","orderService");//对象
                info.put("methodName","getDetail");//方法
                info.put("arg",id);//参数
                OrderEntiry result = null;
                try {
                    result = (OrderEntiry)infoService.passInfo(info);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return result;
            }
        };
        return service;
    }
}
