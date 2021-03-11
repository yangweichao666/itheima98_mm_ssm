package com.itheima.mm.controller;


import com.itheima.mm.entry.Result;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.UserService;
import com.itheima.mm.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.itheima.mm.constant.Constants.LOGIN_USER;

/**
 * @Author YWC
 * @Date 2021/2/2 16:17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
  private UserService userService ;
    @RequestMapping("/login")
    public Result login(@RequestBody User user, HttpSession session)   {
        try {
            //接受求求参数并封装在json中

            //调用业务层的方法

            User loginUser=userService.login(user);
            //将login方法的参数放入session中
            session.setAttribute(LOGIN_USER, loginUser);
            //成功查询时 将result 转换成jsession对象
            Result result = new Result(true,"登录成功",loginUser);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            //当出错时返回
               return new Result(false,e.getMessage(),null);

        }


    }

    /**
     * 退出登录
     * @param
     */
    @RequestMapping("/logout")
    public  Result  logout(HttpSession session) throws IOException {
        //销毁session
        session.invalidate();
        //向客户端响应数据
        return new Result(true,"退出登录成功",null);
    }
}
