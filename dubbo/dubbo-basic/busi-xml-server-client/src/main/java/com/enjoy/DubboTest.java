package com.enjoy;

import com.alibaba.dubbo.rpc.service.EchoService;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DubboTest {

    /**
     * 回声测试：扫一遍服务是否都已就绪
     */
    @Test
    public void echoTest() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/dubbo-Test.xml");

        ctx.start();
        System.out.println("---------dubbo启动成功--------");
        String[] serviceIds = new String[]{"errorService","userService","orderService","payService"};

        Object ret = null;
        for (String id:serviceIds){
            try {//reference代理对象本身继承了EchoService，所以这里可以强制转换为EchoService
                EchoService echoService = (EchoService)ctx.getBean(id);
                ret = echoService.$echo("ok"); //判断服务是否存活 存活返回ok
            } catch (Exception e) {
                ret = "not ready";
            }
            System.out.println("service:"+id+":"+ret);
        }
    }

    /**
     * 泛化调用
     * 当前项目，没有对应的接口---- com.enjoy.service.OtherService
     */
    @Test
    public void other() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/dubbo-Test.xml");

        ctx.start();
        System.out.println("---------dubbo启动成功--------");
        GenericService genericService = (GenericService)ctx.getBean("unknownService");

        Object ret = genericService.$invoke("doSomething",new String[]{"java.lang.String"},new Object[]{"pency"});
        System.out.println("调用结果："+ret.toString());
    }

}
