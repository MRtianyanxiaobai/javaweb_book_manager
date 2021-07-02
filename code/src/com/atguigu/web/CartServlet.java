package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Cart;
import com.atguigu.pojo.CartItem;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet{
    BookService bookService = new BookServiceImpl();
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        Book book =bookService.queryBookById(id);
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        if(cart==null){
            cart =new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
        System.out.println("请求头Referer 的值：" + req.getHeader("Referer"));
        req.getSession().setAttribute("lastName",cartItem.getName());
        // 重定向回原来商品所在的地址页面
        resp.sendRedirect(req.getHeader("Referer"));

    }
    //利用Ajax做页面的局部更新
    protected  void ajaxAddItem(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
        //获取请求的参数
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        Book book =bookService.queryBookById(id);
        CartItem cartItem = new CartItem(book.getId(),book.getName(),1,book.getPrice(),book.getPrice());
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        //将cart添加到session中
        if(cart==null){
            cart =new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
        //将关键数据添加到json中实现局部更新的效果
        Map<String,Object> reslutMap = new HashMap<>();
        reslutMap.put("totalCount",cart.getTotalCount());
        reslutMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String reslutMapjsonString = gson.toJson(reslutMap);
//        System.out.println(reslutMapjsonString);
        resp.getWriter().write(reslutMapjsonString);
    }


    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        //获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart!=null){
            //删除购物车的商品项
            cart.deleteItem(id);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    protected  void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        //重新下回到购物车页面
        response.sendRedirect(request.getHeader("Referer"));
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"),0);
        int count  = WebUtils.parseInt(request.getParameter("count"),1);
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart!=null){
            cart.updateCount(id,count);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
}
