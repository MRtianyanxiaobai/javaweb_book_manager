package com.atguigu.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//所有Servlet继承该类，通过request闯过来的字符串，直接调用相关函数
public abstract class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // 解决响应的中文乱码
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        // 获取action 业务鉴别字符串，获取相应的业务方法反射对象
        //通过方法名以及参数类型和个数反射得到的具体的方法
//        Method method = null;
//        System.out.println("调用post函数");
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            System.out.println(method);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            //如果方法出现错误，则抛出异常到Filter中
            throw new RuntimeException(e);
        }

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("调用Get函数!");
        doPost(request, response);
    }
}
