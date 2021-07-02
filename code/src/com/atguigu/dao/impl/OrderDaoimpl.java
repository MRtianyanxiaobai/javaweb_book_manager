package com.atguigu.dao.impl;

import com.atguigu.dao.OrderDao;
import com.atguigu.pojo.Order;

import java.util.List;

public class OrderDaoimpl extends BaseDao implements OrderDao {

    @Override
    public int saveOrder(Order order) {
        String sql ="insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        return  update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
//        return 0;
    }

    @Override
    public List<Order> queryMyOrders(int userId) {
        String sql = "select `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId from t_order where user_id = ?";
        return querryForList(Order.class,sql,userId);
    }

    @Override
    public List<Order> queryAllOrders() {
        String sql = "select `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId from t_order";
        return  querryForList(Order.class,sql);
    }

    @Override
    public void updateOrderStatus(int status, String orderId) {
        // sql语句
        String sql = "update t_order set status = ? where order_id   = ?";
        // 执行sql语句
        update(sql, status, orderId);
    }

    @Override
    public Order queryTheOrder(String orderId) {
        String sql = "select `order_id` orderId,`create_time` createTime,`price`,`status`,`user_id` userId from t_order where order_id = ?";
        return queryForone(Order.class,sql,orderId);
    }
}
