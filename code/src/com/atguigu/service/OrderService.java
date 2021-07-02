package com.atguigu.service;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    public  String createOrder(Cart cart,Integer userId);
    // 查找自己的订单
    public List<Order> queryMyOrders(int userId);
    // 查找某个订单的订单项
    public List<OrderItem> queryOrderItems(String orderId);
    //查找所有订单
    public List<Order> queryAllOrders();
    // 修改订单状态为已发货
    public void sendOrder(String orderId);
    // 修改订单状态为已收获
    public void receivedOrder(String orderId);
    //查询某个订单
    public  Order querryOrder(String orderId);
}