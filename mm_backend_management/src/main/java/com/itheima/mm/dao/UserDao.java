package com.itheima.mm.dao;

import com.itheima.mm.pojo.User;

/**
 * @Author YWC
 * @Date 2021/2/2 16:45
 */
public interface UserDao {
   User findByUsername( String username);
}
