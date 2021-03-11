package com.itheima.mm.service;

import com.itheima.mm.pojo.User;

/**
 * @Author YWC
 * @Date 2021/3/6 19:17
 */
public interface UserService {
    User login(User user) throws Exception;
}
