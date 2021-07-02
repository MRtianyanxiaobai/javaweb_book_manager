package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.dao.impl.OrderDaoimpl;
import com.atguigu.dao.impl.OrderItemDaoimpl;
import com.atguigu.pojo.*;
import com.atguigu.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    //联动，创建订单后，显示界面的库存也应该都减去相应的count
    private OrderDao orderDao = new OrderDaoimpl();
    private OrderItemDao orderItemDao = new OrderItemDaoimpl();
    private BookDao bookDao = new BookDaoImpl();
    @Override
    public String createOrder(Cart cart, Integer userId) {
//        return null;
        //唯一的订单号
        String orderId = System.currentTimeMillis()+""+userId;
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        orderDao.saveOrder(order);
        for (Map.Entry<Integer, CartItem> entry:cart.getItems().entrySet()){
             CartItem item = entry.getValue();
             OrderItem orderItem = new OrderItem(null,item.getName(),item.getCount(),item.getPrice(),item.getTotalPrice(),orderId);
             orderItemDao.saveOrderItem(orderItem);

             //更新库存和数据库
            Book book = bookDao.queryBookById(item.getId());
            book.setStock(book.getStock()-item.getCount());
            book.setSales(book.getSales()+item.getCount());
            bookDao.updateBook(book);
        }
        //结账后清空购物车
        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> queryMyOrders(int userId) {

        return  orderDao.queryMyOrders(userId);
    }
    @Override
    public List<Order> queryAllOrders() {
        return orderDao.queryAllOrders();
    }
    @Override
    public List<OrderItem> queryOrderItems(String orderId) {
        return orderItemDao.queryOrderItems(orderId);
    }


    @Override
    public void sendOrder(String orderId) {

        orderDao.updateOrderStatus(1,orderId);
    }

    @Override
    public void receivedOrder(String orderId) {
        orderDao.updateOrderStatus(2,orderId);
    }

    @Override
    public Order querryOrder(String orderId) {
        return  orderDao.queryTheOrder(orderId);
//        return null;
    }

}
