package com.enjoy.dubbospi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Filter;
import org.junit.Test;

import java.util.List;

/**
 * 某此情况下，同一个接口的多个实现需要同时发挥作用，比如filter链。此时需要按条件选择一批实现类来工作
 * Activate：可以被框架中自动激活加载扩展
 * 用户通过group和value配置激活条件
 * group 分组(筛选条件)
 * value url中包含的key名(筛选条件)
 * order 排序
 *
 * 下面以filter过滤器为例讲解
 * 1.如果需要在服务暴露时装载，那么group="provider"
 * 2.如果需要在服务引用的时候装载，那么group="consumer"
 * 3.如果想被暴露和引用时同时被装载，那么group={"consumer", "provider"}
 * 4.如果需要url中有某个特定的值才被加载，那么value={"test5", "test"}
 */
public class ActivateTest {

    /**
     * 调用分组为pency过滤器   FilterC、FilterD被调用
     * （FilterE虽然也是pency分组 但不满足URL中有key为test5的参数）
     */
    @Test
    public void testActivate1() {
        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);

        URL url = URL.valueOf("test://localhost/user"); //url 理解调用rpc服务的url 只有这个是变化的
        List<Filter> list=extensionLoader.getActivateExtension(url,"", "pency");//group
        for (Filter filter:list){
            filter.invoke(null,null);
        }
    }

    /**
     * 分组为james   url中有参数test5
     */
    @Test
    public void testActivate2() {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);

        URL url = URL.valueOf("test://localhost/dsafsdfsdfsd");
        url = url.addParameter("test5", "66666");
        List<Filter> list = extensionLoader.getActivateExtension(url,"","james");
        for (Filter filter:list){
            filter.invoke(null,null);
        }
    }

    /**
     * 分组为pency
     * url中有参数test5
     * url中指定要使用a,去除c实现
     * 结果：原本是 cde  又+了A -了c  所以最后 ade
     */
    @Test
    public void testActivate3() {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);

        URL url = URL.valueOf("test://localhost/test");
        url = url.addParameter("test5", "qqqq"); //value
        url = url.addParameter("myfilter", "a,-c");
        //getActivateExtension(URL url, String key, String group)
        // key是传入的是url中包含的一个参数 在符合规则的实现类基础上再按key对应value再扩展激活规则 -代表剔除  myfilter这里的规则表示 加上a 剔除c。a c就是配置文件中的key
        List<Filter> list = extensionLoader.getActivateExtension(url,"myfilter","pency");
        // 一个符合规则的实现类上注解示例： @Activate(group = {"pency","XXX"},order = 1,value = "test5")
        for (Filter filter:list){
            filter.invoke(null,null);
        }
    }
}
