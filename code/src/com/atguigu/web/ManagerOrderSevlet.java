package com.atguigu.web;

import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerOrderSevlet extends  BaseServlet {
    private OrderService orderService = new OrderServiceImpl();


    protected void orders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders = orderService.queryAllOrders();
        request.setAttribute("orders",orders);
        request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request,response);
    }
    protected void sendOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //确认发货
        String orderId =  request.getParameter("orderId");
//        orderService.
        Order order = orderService.querryOrder(orderId);
        if (order==null || order.getStatus()!=0){
            response.sendRedirect(request.getHeader("referer"));
        }
        orderService.sendOrder(orderId);
        response.sendRedirect(request.getHeader("referer"));

    }
    protected  void OrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String orderId = request.getParameter("orderId");
        List<OrderItem> orderitems = orderService.queryOrderItems(orderId);

        request.setAttribute("orderitems",orderitems);
        request.getRequestDispatcher("/pages/manager/order_detai.jsp").forward(request,response);

    }

}
