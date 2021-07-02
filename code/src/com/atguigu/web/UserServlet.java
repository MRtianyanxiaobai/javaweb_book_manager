package com.atguigu.web;

import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.service.impl.UserServiceImpl;
import com.atguigu.utils.WebUtils;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//UserServlet: 统一处理login和regist
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        // 1、获取请求的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println(" " + username+password);
        // 调用userService.login()登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        // 如果等于null,说明登录失败!
        if (loginUser == null) {
        // 把错误信息，和回显的表单项信息，保存到Request 域中
            req.setAttribute("msg","用户或密码错误！");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        // 跳回登录页面
        }else{
            req.getSession().setAttribute("user",loginUser);
            // 登录成功
            //跳到成功页面login_success.html
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req, resp);
        }
    }
    /**
     * 处理注册的功能
     * @param response
     * @param request
     * @throws ServletException
     * @throws IOException
     */
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException{
       //获取session中的验证码
        String token = (String)request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //删除原验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        // 1、获取请求的参数
        //参数众多使用 BeanUtils 方法一次性将所有参数注入到指定的javaBean中
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");
        System.out.println(" "+username+" "+password+" "+ code);
        User user = WebUtils.copyParamToBean(request.getParameterMap(),new User());
        // 2、检查验证码是否正确=== 写死,要求验证码为:abcde
//        if ("abcde".equalsIgnoreCase(code)) {
            // 3、检查用户名是否可用
          if(token!=null && token.equalsIgnoreCase(code)){
            if (userService.existsUsername(username)) {
                System.out.println("用户名[" + username + "]已存在!");
                // 把回显信息，保存到Request 域中
                request.setAttribute("msg", "用户名已存在！！");
                request.setAttribute("username", username);
                request.setAttribute("email", email);

                // 跳回注册页面
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);
            } else {
                // 可用
                // 调用Sservice 保存到数据库
                userService.registUser(new User(null, username, password, email));
                // 跳到注册成功页面regist_success.html
                //这个跳转的过程是get，相当于在输入框里进行回车
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("msg", "验证码错误！！");
            request.setAttribute("username", username);
            request.setAttribute("email", email);

            System.out.println("验证码[" + code + "]错误");
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request, response);

        }
    }


    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        销货所有session
        request.getSession().invalidate();
        //重定向返回首页，项目默认页面
        response.sendRedirect(request.getContextPath());

    }
    protected  void ajaxExistsUsername(HttpServletRequest req,HttpServletResponse resq) throws ServletException,IOException{
        //获取username
        String username = req.getParameter("username");
        Boolean existUsername = userService.existsUsername(username);
        Map<String,Object> reslutMap = new HashMap<>();
        reslutMap.put("existUsername",existUsername);
        Gson gson = new Gson();
        String json = gson.toJson(reslutMap);
        resq.getWriter().write(json);


    }
}
