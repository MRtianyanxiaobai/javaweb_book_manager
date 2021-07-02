package com.atguigu.test;

import com.atguigu.dao.BookDao;
import com.atguigu.dao.impl.BookDaoImpl;
import com.atguigu.pojo.Book;
import org.junit.Test;

import java.math.BigDecimal;

public class BookDaoTest {

    BookDao bookDao = new BookDaoImpl();
    @Test
    public void addBook() {
        bookDao.addBook(new Book(null, "国哥为什么这么帅！", "191125", new
                BigDecimal(9999), 1100000, 0, null
        ));

    }
    @Test
    public void deleteBook(){
        bookDao.deleteBookById(21);
    }
    @Test
    public  void updateBook(){
        bookDao.updateBook(new Book(21,"杨强的老年生活","杨强",new BigDecimal(9999),1100000,0,null));
    }
    @Test
    public  void  queryBookByID(){
        System.out.println(bookDao.queryBookById(21));

    }
    @Test
    public  void queryBooks(){
        for(Book queryBook:bookDao.queryBooks()){
            System.out.println(queryBook);
        }
    }
}
