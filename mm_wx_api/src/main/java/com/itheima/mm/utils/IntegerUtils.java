package com.itheima.mm.utils;

/**
 * @Author YWC
 * @Date 2021/2/22 17:54
 */
public class IntegerUtils {
    public static Integer  parseInteger(Object value){
        if (value instanceof Integer){
            return (Integer) value;
        }else {
            return Integer.valueOf((String)value);
        }
    }
}
