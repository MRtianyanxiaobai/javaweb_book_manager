package com.atguigu.dao.impl;

import com.atguigu.dao.OrderItemDao;
import com.atguigu.pojo.OrderItem;

import java.util.Collection;
import java.util.List;

public class OrderItemDaoimpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(), orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItems(String orderId) {
        // sql语句
        String sql = "select `id`,`name`,`count`,`price`,`total_price` totalPrice,`order_id` orderId from  t_order_item where order_id = ?";
        // 执行sql语句
        List<OrderItem> result = querryForList(OrderItem.class,sql, orderId);
        // 返回
        return result;
    }

    @Override
    public void batchSaveOrderItem(Collection<OrderItem> items) {
        //批量处理
        // 插入sql语句
        String sql = "insert into t_order_item(`name`,`count`,`price`,`total_price`,`order_id`) values(?,?,?,?,?)";
        // 创建一个二组数组
        Object[][] pramas = new Object[items.size()][5];
        int i = 0;
        for (OrderItem item:items){
            Object[] oneParamas = pramas[i];
            oneParamas[0] = item.getName();
            oneParamas[1] = item.getPrice();
            oneParamas[2] = item.getTotalPrice();
            oneParamas[3] = item.getCount();
            oneParamas[4] = item.getOrderId();
            i++;
        }
        //批处理提交事务
        batch(sql,pramas);

    }
}
