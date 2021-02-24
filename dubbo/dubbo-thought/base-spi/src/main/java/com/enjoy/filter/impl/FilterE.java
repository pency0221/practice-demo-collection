package com.enjoy.filter.impl;


import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * 使用方传递了group = pency或james，并且url中包含test5参数，则该Filter激活
 */
@Activate(group = {"pency","james"},order = 1,value = "test5")
public class FilterE implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("你好，调通了Filer E实现！");
        return null;
    }
}
