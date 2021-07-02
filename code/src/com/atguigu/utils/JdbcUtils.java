package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import java.sql.Connection;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static DruidDataSource dataSource;
    //为了保证多线程情况下不会发生思索，这里使用ThreadLocal来保存当前线程下的conn
    //这样就可以保证所有的操作在同一个事务中进行
    private  static  ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
    //全局静态，只进行一次连接
    static {
        try {
            //尝试连接数据库
            Properties peroperties = new Properties();
            // 读取属性的配置文件，通过映射
            InputStream inputstram = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            peroperties.load(inputstram);
            //创建数据连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(peroperties);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取数据库连接池中的连接
     * @return 如果返回null,说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnetion(){
        Connection conn = conns.get();//获得当前线程下的conn
        if (conn==null){
            try {
                conn = dataSource.getConnection();//获得连接池
                conns.set(conn);//保存到localThread对象中
                conn.setAutoCommit(false);//设置为手动管理事务
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return conn;
    }
    /**
     * 关闭连接，放回数据库连接池
     *
     */
    public  static  void  commitAndclose()  {
        Connection connection = conns.get();
        if(connection!=null){//证明使用个链接，这个时候需要提交事务，一次性执行所有sql
            try {
                connection.commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
        conns.remove();//关闭当前线程下的连接

    }
    /**
     * 回滚事务并关闭连接
     */
    public  static  void rollbackAndClose(){
        Connection connection = conns.get();
        if (connection!=null){
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                ;
            }
        }
        // 一定要执行remove 操作，否则就会出错。（因为Tomcat 服务器底层使用了线程池技术）
        conns.remove();
    }
}
