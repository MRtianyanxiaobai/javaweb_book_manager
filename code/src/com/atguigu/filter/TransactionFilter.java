package com.atguigu.filter;

import com.atguigu.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤所有Servlet，如果Servlet报错，那么则将其跳转到指定的错误页面
 */
public class TransactionFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      try {
          filterChain.doFilter(servletRequest,servletResponse);//相当于继续执行，如果报错则会转入catch
          JdbcUtils.commitAndclose();//提交事务，且关闭事务
      }catch (Exception e){
          JdbcUtils.rollbackAndClose();//如果出现错误，所有的数据库操作应该回滚
          e.printStackTrace();
          throw new  RuntimeException(e);//把异常抛给Tomcat管理展示友好的错误页面
      }

    }

    @Override
    public void destroy() {

    }
}
