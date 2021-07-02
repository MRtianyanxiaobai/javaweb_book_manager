package com.atguigu.test;

import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderItemDao;
import com.atguigu.dao.impl.OrderDaoimpl;
import com.atguigu.dao.impl.OrderItemDaoimpl;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.utils.JdbcUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrderDaoTest {
    @Test
    public  void saveOrderTest(){
        OrderDao orderDao = new OrderDaoimpl();
        orderDao.saveOrder(new Order("1234567890",new Date(),new BigDecimal(100),0, 1));
    }
    @Test
    public  void queryMyOrdersAndAllOrders(){
        OrderDao orderDao = new OrderDaoimpl();
        List<Order> list_userid = orderDao.queryMyOrders(1);
        List<Order> list_all = orderDao.queryAllOrders();
        JdbcUtils.commitAndclose();//提交事务，且关闭事务
        System.out.println("userid:"+1);
        for (Order order:list_userid ) {
            System.out.println(order.toString());
//            System.out.println(order.getUserId()+" : "+order.getPrice());

        }
        System.out.println("==============================");
        for (Order order:list_all ) {
//            System.out.println(order.getUserId()+" : "+order.getPrice());
            System.out.println(order);

        }

    }
    @Test
    public  void updatestats(){
        OrderDao orderDao = new OrderDaoimpl();
        List<Order> list_all = orderDao.queryAllOrders();
//        JdbcUtils.commitAndclose();//提交事务，且关闭事务
        System.out.println("==============================");
        for (Order order:list_all ) {
            System.out.println(order);
            orderDao.updateOrderStatus(2,order.getOrderId());

        }
        System.out.println("==============================");
        list_all = orderDao.queryAllOrders();
        for (Order order:list_all ) {
            System.out.println(order);
//            orderDao.updateOrderStatus(2,order.getOrderId());

        }
        JdbcUtils.commitAndclose();//提交事务，且关闭事务
    }

    //orderDao的测试
    @Test
    public  void OrderItemDaoTest(){
        OrderItemDao orderItemDao = new OrderItemDaoimpl();
//        Collection<OrderItem> orderItems = new LinkedList<>();
//        orderItems.add(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new
//                BigDecimal(100),"16251308521147"))
        orderItemDao.saveOrderItem(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new
                BigDecimal(100),"16251308521147"));
        orderItemDao.saveOrderItem(new OrderItem(null,"javaScript 从入门到精通", 2,new
                BigDecimal(100),new BigDecimal(200),"16251308521147"));
        orderItemDao.saveOrderItem(new OrderItem(null,"Netty 入门", 1,new BigDecimal(100),new
                BigDecimal(100),"16251308521147"));

    }
    @Test
    public  void OrderItemDaoBatchTest(){
        OrderItemDao orderItemDao = new OrderItemDaoimpl();
        Collection<OrderItem> orderItems = new LinkedList<>();
        orderItems.add(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new BigDecimal(100),"16251308521147"));
        orderItems.add(new OrderItem(null,"java 从入门到精通", 1,new BigDecimal(100),new BigDecimal(100),"16251308521147"));
        orderItems.add(new OrderItem(null,"Netty 入门", 1,new BigDecimal(100),new BigDecimal(100),"16251308521147"));
        System.out.println(orderItems);
        orderItemDao.batchSaveOrderItem(orderItems);
        JdbcUtils.commitAndclose();//提交事务，且关闭事务
    }
    @Test
    public  void OrderListItem(){
        OrderItemDao orderItemDao = new OrderItemDaoimpl();

        List<OrderItem> items = orderItemDao.queryOrderItems("16251308521147");
        for(OrderItem item:items){
            System.out.println(item);
        }
    }

}
