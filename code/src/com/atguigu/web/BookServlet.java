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
import java.util.List;

public class BookServlet extends BaseServlet {
    BookService bookService = new BookServiceImpl();
    protected  void  list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        System.out.println();

        List<Book> books = bookService.queryBooks();
        req.setAttribute("books",books);
      //  System.out.println("数据处理完毕了，开始转发了");
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }
    protected  void  add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//        resp.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html;charset=utf-8");
//        String username = new String(req.getParameter("name").getBytes("iso-8859-1"),"utf-8");
//        String author = new String(req.getParameter("author").getBytes("iso-8859-1"),"utf-8");

//        System.out.println("===="+username+author);
        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());
//        book.setName(WebUtils.parsePostencode(book.getName()));
//        book.setAuthor(WebUtils.parsePostencode(book.getAuthor()));
        System.out.println("添加的书籍名称:"+book);
        bookService.addBook(book);
        //使用重定向的方式跳转请求
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo=1000");

    }
    protected  void getBookbook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //转为字符串，如果输入的id不为正规数字，那么则返回默认数字0
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        Book book = bookService.queryBookById(id);
        req.setAttribute("book",book);
        req.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(req,resp);
    }
    protected  void  update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//        req.setCharacterEncoding("utf-8");
//        resp.setCharacterEncoding("utf-8");
//        resp.setContentType("text/html;charset=utf-8");


        Book book = WebUtils.copyParamToBean(req.getParameterMap(),new Book());
//        book.setName(WebUtils.parsePostencode(book.getName()));
//        book.setAuthor(WebUtils.parsePostencode(book.getAuthor()));
        bookService.updateBook(book);
        //使用重定向的方式跳转请求
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));
    }
    protected  void  delete(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        bookService.deleteBookById(id);

//        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=list");
        resp.sendRedirect(req.getContextPath()+"/manager/bookServlet?action=page&pageNo="+req.getParameter("pageNo"));


    }
    /**
     * 处理分页功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected  void page(HttpServletRequest req,HttpServletResponse resp) throws  ServletException,IOException{
        //获取pageNo 和 pageSize
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = Page.PAGE_SIZE;
        if(req.getParameter("pageSize")!=null){
             pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        }
        //查询获取新的page
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("manager/bookServlet?action=page");
//        req.setAttribute();
        req.setAttribute("page",page);

        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req,resp);
    }


}
