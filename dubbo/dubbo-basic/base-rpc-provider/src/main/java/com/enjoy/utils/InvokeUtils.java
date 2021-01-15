package com.enjoy.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Peter on 8/28 028.
 */
public class InvokeUtils {
    public static ClassPathXmlApplicationContext context;

    /**
     * java反射
     * @param target 目标对象
     * @param methodName 目标方法
     * @param argTypes 方法参数类型
     * @param args 实参
     * @return
     */
    public static Object call(Object target,String methodName,
                                Class[] argTypes,Object[] args)
            throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {

        Method method = target.getClass().getMethod(methodName, argTypes);
        return method.invoke(target, args);
    }

    public static Object call(Map<String,String> info, ApplicationContext ctx){

        String targetStr = info.get("target");
        String methodName = info.get("methodName");
        String arg = info.get("arg");

        try {
            return call(ctx.getBean(targetStr), methodName,new Class[]{String.class},new Object[]{arg});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
