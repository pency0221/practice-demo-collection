package com.enjoy.dubbospi;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.enjoy.service.InfoService;
import com.enjoy.service.OrderService;
import org.junit.Test;

/**
 * SPI解决了实例的加载问题，并对实现 配置了健值对的形式进行管理
 * 但是,实际工作中，我们希望实例能懒加载，或者运行期再抉择
 * 如：InfoService的实现有三种，具体要用哪一个，在编译期未知(延迟加载考虑，只有在运行期实际需要时再加载)
 * 自适应注解@Adaptive解决这个选择问题
 *
 * 使用位置:①实现类上 ②接口的方法上(为这个接口方法生成代理逻辑 代理具体哪个实现类的方法 url中指定)
 * 注解在类上时，直接选此类为适配类（一般只有一个实现类有标记，有多个 也只会选一个 选的是配置中最靠前带有标注的实现类）
 * 注解在接口的方法上时，只为此方法生成代理逻辑（方法必须有参数为url）
 *
 * getAdaptiveExtension选实现类顺序：实现类上有@Adaptive>URL key指定>@SPI(“xxxx”)默认(接口方法上需要有@Adaptive 不然到这一步还没找到 就报错了)
 * （如果以上顺序都没选到 接口方法上又加了@Adaptive url里还没有key参数  报错说url没默认接口.驼峰参数名）
 */
public class AdaptiveTest {

    /**
     * 场景:C实现类上有@Adaptive 、无论接口@SPI有没有指定 无论URL有没有参数指定  都选择C实现
     * (如果此实例C实现上也没有@Adaptive  @SPI也没有默认 调用loader.getAdaptiveExtension()报错No adaptive method on extension
     * 需要接口方法上添加@Adaptive并且url上指定或有@SPI默认
     */
    @Test
    public void test1(){
        ExtensionLoader<InfoService> loader = ExtensionLoader.getExtensionLoader(InfoService.class);
        //要么接口方法上有@Adaptive 要么实现类上有 不然报错 （但最终可以获取的可以是@SPI默认的）
        InfoService adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test");
        System.out.println(adaptiveExtension.passInfo("james", url));
    }
    /**
     * 场景：各个实现类上面没有@Adaptive、SPI上有注解@SPI("b"), url无参数
     * 结果：则以@SPI("b")为准，选择B实现  就相当于调用的是getDefaultExtension();
     * ps:（前提是接口方法上需要有@Adaptive 不然报错 No adaptive method on extension）
     */
    @Test
    public void test2(){
        ExtensionLoader<InfoService> loader = ExtensionLoader.getExtensionLoader(InfoService.class);
        InfoService adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test");
        System.out.println(adaptiveExtension.passInfo("james", url));
    }
    /**
     * 场景：各个实现类上面没有@Adaptive、SPI上有注解@SPI("b")、接口方法passInfo标注
     *  URL中有具体的值info.service=a, info.service 是接口InfoService的驼峰"."分割
     * 结果：则以URL为准，选择A实现
     */
    @Test
    public void test3(){
        ExtensionLoader<InfoService> loader = ExtensionLoader.getExtensionLoader(InfoService.class);
        InfoService adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?info.service=a");
        System.out.println(adaptiveExtension.passInfo("james", url));
    }
    /**
     * 场景：各个实现类上面没有@Adaptive、接口方法加注解@Adaptive({"pi"}),URL指定默认参数key是接口驼峰但这里在接口的方法上重定义了key
     * URL中有具体的值pi=c,
     * 结果：则以URL中的pi参数为准，选择C实现
     */
    @Test
    public void test4(){
        ExtensionLoader<InfoService> loader = ExtensionLoader.getExtensionLoader(InfoService.class);
        InfoService adaptiveExtension = loader.getAdaptiveExtension();
        URL url = URL.valueOf("test://localhost/test?info.service=a&pi=c");
        System.out.println(adaptiveExtension.passInfo("james", url));
    }

/*    @Test
    public void iocSPI() {
        //获取OrderService的 Loader 实例
        ExtensionLoader<OrderService> loader = ExtensionLoader.getExtensionLoader(OrderService.class);
        //取得默认拓展类
        OrderService orderService = loader.getDefaultExtension();
        URL url = URL.valueOf("test://localhost/test?info.service=b");
        orderService.getDetail("peter",url);
    }*/

}
