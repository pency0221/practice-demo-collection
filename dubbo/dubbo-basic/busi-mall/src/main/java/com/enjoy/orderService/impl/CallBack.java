package com.enjoy.orderService.impl;

/**
 * 定义的回调类
 */
public class CallBack {
    //第一个参数，为返回结果值，后续参数是入参
    public void onSuccess(String result, String arg){
        System.out.println("回调测试，调用成功："+ result);
        //处理业务逻辑
    }
    //第一个参数，为返回结果值，后续参数是入参
    public void onError(Throwable ex,String arg){
        System.out.println("回调测试，调用异常，请紧急处理,异常原因："+ ex.getMessage());
        //记录警告信息
    }
}
