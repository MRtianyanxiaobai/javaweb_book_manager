package com.atguigu.test;

import com.atguigu.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

public class JdbcUtilsTest {
   @Test
   public void testJdbcUtils(){
       for(int i=0;i<100;i++){
           Connection conn = JdbcUtils.getConnetion();
           System.out.println(conn);
//           JdbcUtils.close(conn);
            JdbcUtils.commitAndclose();
       }

   }
}

