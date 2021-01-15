package com.enjoy.controller;

import com.enjoy.entity.OrderEntiry;
import com.enjoy.entity.ProductEntiry;
import com.enjoy.entity.UserEntiry;
import com.enjoy.service.OrderService;
import com.enjoy.service.ProductService;
import com.enjoy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class IndexController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ProductEntiry productEntiry = productService.getDetail("1");
        OrderEntiry orderView = orderService.getDetail(id);

        request.setAttribute("productView", productEntiry);
        request.setAttribute("orderView", orderView);
        return "index";
    }

}
