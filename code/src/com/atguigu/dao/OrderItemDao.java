package com.atguigu.dao;

import com.atguigu.pojo.OrderItem;

import java.util.Collection;
import java.util.List;

public interface OrderItemDao {
    public  int saveOrderItem(OrderItem orderItem);
    public List<OrderItem> queryOrderItems(String orderId);
    //批量存储Items
    public void batchSaveOrderItem(Collection<OrderItem> items);

}
