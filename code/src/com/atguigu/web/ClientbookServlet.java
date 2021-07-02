package com.atguigu.web;

import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;
import com.atguigu.service.impl.BookServiceImpl;
import com.atguigu.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientbookServlet extends BaseServlet {
    BookService bookService = new BookServiceImpl();
    protected  void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = Page.PAGE_SIZE;
        if(req.getParameter("pageSize")!=null){
            pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        }
        //查询获取新的page
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("client/bookServlet?action=page");
//        req.setAttribute();
        req.setAttribute("page",page);

        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);
    }
    protected void  pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理价格区间的分页
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(req.getParameter("min"), 0);
        int max = WebUtils.parseInt(req.getParameter("max"), Integer.MAX_VALUE);

        //查找一定范围类的page
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);

        //设置url
        StringBuilder sb = new StringBuilder("client/bookServlet?action=pageByPrice");
        //这里min和max会一直保留
        // 如果有最小价格的参数,追加到分页条的地址参数中
        if (req.getParameter("min") != null) {
            sb.append("&min=").append(req.getParameter("min"));
        }
        // 如果有最大价格的参数,追加到分页条的地址参数中
        if (req.getParameter("max") != null) {
            sb.append("&max=").append(req.getParameter("max"));
        }
        page.setUrl(sb.toString());
        //3 保存Page对象到Request域中
        req.setAttribute("page",page);
        //4 请求转发到pages/manager/book_manager.jsp页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);



    }

}
