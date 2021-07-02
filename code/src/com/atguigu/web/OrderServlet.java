package com.atguigu.web;

import com.atguigu.pojo.Cart;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderItem;
import com.atguigu.pojo.User;
import com.atguigu.service.OrderService;
import com.atguigu.service.impl.OrderServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//clientOrderServlet
public class OrderServlet  extends  BaseServlet{
    private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected  void  createOrder(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        //获取cart对象
        Cart cart  = (Cart) req.getSession().getAttribute("cart");
        //后去uid
        //如果user不存在，则跳转到登录界面
        User loginUser = (User) req.getSession().getAttribute("user");
        if (loginUser==null){
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            return;
        }
        Integer userId = loginUser.getId();
        //创建订单，返回订单号
        String orderId = orderService.createOrder(cart,userId);
        //測試全局filter是否能够事务回滚设置一个异常
        // int i = 12/0;

        //跳转到确认页面
        req.getSession().setAttribute("orderId",orderId);
//        System.out.println("req.getContextPath():"+req.getContextPath());
//        System.out.println("req.getHeader(\"Referer\"):"+req.getHeader("Referer"));
        resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }

    protected void myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Integer order_id = WebUtils.parseInt(request.getParameter("orderId"))
        User loguser = (User) request.getSession().getAttribute("user");
        if (loguser==null){
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
            return;
        }else {
            List<Order> orders = orderService.queryMyOrders(loguser.getId());
//            System.out.println(orders);
            request.setAttribute("myOrders",orders);
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request,response);
        }

    }

    protected void receiveOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //确认收获
        String orderId =  request.getParameter("orderId");
//        orderService.
        Order order = orderService.querryOrder(orderId);
        if (order==null || order.getStatus()!=1){
            response.sendRedirect(request.getHeader("referer"));
        }
        orderService.receivedOrder(orderId);
        response.sendRedirect(request.getHeader("referer"));

    }
    protected  void OrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String orderId = request.getParameter("orderId");
//        Order order = orderService.querryOrder(orderId);
        List<OrderItem> orderitems = orderService.queryOrderItems(orderId);

//        Cart cart = new Cart();
//        cart.setItems(orderitems);


        request.setAttribute("orderitems",orderitems);
        request.getRequestDispatcher("/pages/order/order_detai.jsp").forward(request,response);

    }
}
