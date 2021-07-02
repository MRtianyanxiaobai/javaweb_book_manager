package com.atguigu.dao;

import com.atguigu.pojo.Order;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);
    /**
     * 查询我的订单
     *
     * @param userId
     *            用户id
     *
     * @return 用户的订单
     */
    public List<Order> queryMyOrders(int userId);

    /**
     * 查询所有订单
     *
     * @return 返回所有订单信息
     */
    public List<Order> queryAllOrders();

    /**
     * 修改订单状态
     *
     * @param status
     *  订单的状态
     */
    public void updateOrderStatus(int status,String orderId);

    Order queryTheOrder(String orderId);
}
