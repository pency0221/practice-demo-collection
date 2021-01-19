package com.enjoy.controller;

import com.alibaba.dubbo.rpc.RpcContext;
import com.enjoy.entity.OrderEntiry;
import com.enjoy.entity.ProductEntiry;
import com.enjoy.entity.UserEntiry;
import com.enjoy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Controller
public class IndexController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayService payService;
    @Autowired
    private ErrorService errorService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ProductEntiry productEntiry = productService.getDetail("1");
        OrderEntiry orderView = orderService.getDetail(id);

        request.setAttribute("productView", productEntiry);
        request.setAttribute("orderView", orderView);
        return "index";
    }

    /**
     * 异步并发调用   async=true
     */
    @RequestMapping(value = "/async", method = RequestMethod.GET)
    public String cancel(HttpServletRequest request, HttpServletResponse response)  {
        long start = System.currentTimeMillis();

        //getDetail在dubbo reference中设置了async=true，方法立即返回null,所以此处其实可以不用声明对象来接返回值。
        UserEntiry user = userService.getDetail("1");
        //只有async=true，才能得到此对象，并且调用完异步方法后紧跟这取出这个future 因为后面业务逻辑发生新的调用就会被覆盖，
        Future<UserEntiry> userEntiryFuture = RpcContext.getContext().getFuture();//theadlocal绑定的 取的是当前线程的 所以取值不会发生混乱

        String pay = payService.pay(100); //pency:每一次发生调用当前线程上下文中存的future都会被覆盖，如果发生的调用不是异步调用这里返回的就是null
        Future<String> payFuture = RpcContext.getContext().getFuture();
        //其他业务逻辑...
        try {
            //Future取得返回值，此处才真正阻塞
            user = userEntiryFuture.get();//阻塞的
            pay = payFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        request.setAttribute("user", user);
        request.setAttribute("pay", pay);

        long time = System.currentTimeMillis() - start;
        request.setAttribute("time", time);

        return "/async";
    }

    /**
     * 事件通知
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    @ResponseBody
    public String error(HttpServletRequest request, HttpServletResponse response){
        String msg = errorService.doSomeThing("测试业务稳定性");
        return "complete:"+msg;
    }

}
