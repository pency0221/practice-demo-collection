Dubbo SPI 的核心实现类为 ExtensionLoader，此类的使用几乎遍及 Dubbo 的整个源码体系。   
通过一个接口对应的ExtensionLoader对象获取实现类，每一个Spi接口都对应一个ExtensionLoader对象用来获取实现类。  
获取接口对应的ExensionLoader对象:  
```
ExtensionLoader<InfoService> loader = ExtensionLoader.getExtensionLoader(InfoService.class);
```  
获取到ExtensionLoader对象后ExtensionLoader提供了三个重要的方法用来不同方式获取实现类：getExtension、getActivateExtension、getAdaptiveExtension，分别与@SPI、@Activate、@Adaptive 注解对应：   
- getExtension 方法，对应加载此接口所有的实现类，根据别名返回一个具体实现类：  
```
InfoService infoServiceA = extensionLoader.getExtension("a"); 
```

- getActivateExtension 方法，对应解析加载@Activate 注解对应的实现  返回一堆符合规则的的实现类：  

```
URL url = URL.valueOf("test://localhost/test");
url = url.addParameter("test5", "qqqq"); //value
url = url.addParameter("myfilter", "a,-c");
List<Filter> list = extensionLoader.getActivateExtension(url,"myfilter","pency");
//getActivateExtension(URL url, String key, String group) 
// key是传入的是url中包含的一个参数 在符合规则的实现类基础上再按key对应value再扩展激活规则 -代表剔除  myfilter这里的规则表示 加上a 剔除c。a c就是配置文件中的key
// 一个符合规则的实现类上注解示例： @Activate(group = {"pency","XXX"},order = 1,value = "test5")
```  
    
- getAdaptiveExtension 方法，对应解析加载@Adaptive 注解对应的实现  返回一个代理类 代理url参数指定的一个实现：

```
URL url = URL.valueOf("test://localhost/test?info.service=a&InfoService=c");
InfoService adaptiveExtension = loader.getAdaptiveExtension(); //这里返回一个代理类
adaptiveExtension.passInfo("james", url);  //真正代理哪个实现类 调用方法时通过url确定
```



















 








