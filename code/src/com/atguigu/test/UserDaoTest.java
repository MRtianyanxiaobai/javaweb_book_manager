package com.atguigu.test;

import com.atguigu.dao.UserDao;
import com.atguigu.dao.impl.UserDaoImpl;
import com.atguigu.pojo.User;
import org.junit.Test;

public class UserDaoTest {
    UserDao usedDao = new UserDaoImpl();
    @Test
    public void  queeryByUername(){
        if(usedDao.queryUserByUsername("admin1234")==null){
            System.out.println("用户可用");
        }else{
            System.out.println("已重复");
        }
    }
    @Test
    public void queryUserByUsernameAndPassword() {
        if ( usedDao.queryUserByUsernameAndPassword("admin1234","1234") == null) {
            System.out.println("用户名或密码错误，登录失败");
        } else {
            System.out.println("查询成功");
        }
    }
    @Test
    public void saveUser() {
        System.out.println( usedDao.saveUser(new User(null,"admin1234", "1234", "wzg168@qq.com")) );
    }
}
