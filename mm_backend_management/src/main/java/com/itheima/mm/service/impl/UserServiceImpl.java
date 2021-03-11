package com.itheima.mm.service.impl;

import com.itheima.mm.dao.UserDao;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @Author YWC
 * @Date 2021/2/2 16:24
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(User user) throws Exception {
        User loginUser = null;

            //1. 校验用户名,根据用户名到数据库查找用户

            loginUser = userDao.findByUsername(user.getUsername());

            if (loginUser != null) {
                if (loginUser.getPassword().equals(user.getPassword())) {
                    return loginUser;
                } else {
                    throw new RuntimeException("密码错误");
                }
            } else {
                throw new RuntimeException("用户名错误");
            }


    }

}
