package com.atguigu.dao.impl;


import com.atguigu.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BaseDao {
    //使用Dbutils 操作数据库
    /**
     * update() 方法用来执行：Insert\Update\Delete 语句
     *
     * @return
     *
     **/
    private QueryRunner querRunner = new QueryRunner();

    public int update(String sql,Object... args){
        System.out.println(" BaseDao 程序在[" +Thread.currentThread().getName() + "]中");
        Connection con = JdbcUtils.getConnetion();
        try {
            return querRunner.update(con, sql, args);
        }catch (Exception e){
            e.printStackTrace();
            //查询失败，跑一场
            throw  new RuntimeException(e);
        }
    }
    /**
     * 查询返回一个javaBean 的sql 语句
     *
     * @param type 返回的对象类型
     * @param sql 执行的sql 语句
     * @param args sql 对应的参数值
     * @param <T> 返回的类型的泛型
     * @return
     * **/
    // 利用泛型返回应当有的语句
    //Class<T>在实例化的时候，T要替换成具体类
    public  <T> T  queryForone(Class<T> type,String sql,Object... args){
        Connection con = JdbcUtils.getConnetion();
        try {
            return querRunner.query(con,sql,new BeanHandler<T>(type),args);
        }catch (SQLException e){
            e.printStackTrace();
            //查询失败，跑一场
            throw  new RuntimeException(e);
        }
//        return  null;
    }
    /**
     * 查询返回多个javaBean 的sql 语句
     *
     * @param type 返回的对象类型
     * @param sql 执行的sql 语句
     * @param args sql 对应的参数值
     * @param <T> 返回的类型的泛型
     **/
    public <T> List<T> querryForList(Class<T>  type,String sql,Object... args){
        Connection con = JdbcUtils.getConnetion();
        try{
            return querRunner.query(con,sql,new BeanListHandler<T>(type),args);
        }catch (SQLException e){
            e.printStackTrace();
            //查询失败，跑一场
            throw  new RuntimeException(e);
        }

    }
    /**
     * 执行返回一行一列的sql 语句
     * @param sql 执行的sql 语句
     * @param args sql 对应的参数值
     * @return
     */
    public Object queryForSingleValue(String sql, Object... args){
        Connection conn = JdbcUtils.getConnetion();
        try {
            return querRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败，跑一场
            throw  new RuntimeException(e);
        }
    }

    public Object batch(String sql, Object[][]  paramas){
        Connection conn = JdbcUtils.getConnetion();
        try {
            return querRunner.batch(conn, sql,paramas);
        } catch (Exception e) {
            e.printStackTrace();
            //查询失败，跑一场
            throw  new RuntimeException(e);
        }
    }
}
