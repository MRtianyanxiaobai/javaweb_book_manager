package com.atguigu.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {
    /**
     * 把Map 中的值注入到对应的JavaBean 属性中。
     * @param value
     * @param bean
     */
    public static <T> T copyParamToBean(Map value,T bean)  {
        try{
            System.out.println("注入之前："+bean);
            BeanUtils.populate(bean,value);
            System.out.println("注入之后："+bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    public static int parseInt(String strInt, int defaultValue) {
        try{
            return Integer.parseInt(strInt);
        }catch (Exception e){
//            e.printStackTrace();
        }
        return defaultValue;
    }
    public static String parsePostencode(String str) {
        try{
            String username = new String(str.getBytes("iso-8859-1"),"utf-8");
            return username;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
