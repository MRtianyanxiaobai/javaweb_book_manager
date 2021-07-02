package com.atguigu.service.impl;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.Page;
import com.atguigu.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id) ;
    }

    @Override
    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks() ;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {
       Page<Book> page = new Page<Book>();
       page.setPageSize(pageSize);
       //总记录数
       Integer pageTotalCount = bookDao.queryForPageTotalCount();
       page.setPageTotalCount(pageTotalCount);

        //求总页码
       Integer pageToal = pageTotalCount/pageSize;
       if (pageTotalCount % pageSize>0){
            pageToal+=1;
       }
       page.setPageTotal(pageToal);
       page.setPageNo(pageNo);
       int begin = (page.getPageNo()-1)*pageSize;
       List<Book> items = bookDao.queryForPageItems(begin,pageSize);
       page.setItems(items);
       return page;

    }

    @Override
    public Page<Book> pageByPrice(int pageNo, int pageSize, int min, int max) {
        Page<Book> page = new Page<Book>();
        //设置每页显示的数量,min和max
        page.setPageSize(pageSize);
        int pageTotalCount = bookDao.queryForPageTotalCountByPrice(min,max);
        page.setPageTotalCount(pageTotalCount);

        int pageTotal = pageTotalCount/pageSize;
        if(pageTotalCount%pageSize!=0){
            pageTotal+=1;
        }
        page.setPageTotal(pageTotal);
        page.setPageNo(pageNo);

        //查询设置开始，结束以及条件
        int begin = (page.getPageNo()-1)*pageSize;
        List<Book> items = bookDao.queryForPageItemsByPrice(begin,pageSize,min,max);
        page.setItems(items);
        return  page;

    }
}
