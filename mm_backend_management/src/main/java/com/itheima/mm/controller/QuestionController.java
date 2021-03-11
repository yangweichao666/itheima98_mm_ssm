package com.itheima.mm.controller;


import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.entry.Result;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.service.impl.QuestionServiceImpl;
import com.itheima.mm.utils.DateUtils;

import javax.servlet.http.HttpSession;
import java.util.Date;

import com.itheima.mm.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YWC
 * @Date 2021/2/5 19:42
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/findAllBasePage")
    public Result findAllBase(@RequestBody QueryPageBean queryPageBean){
        try {

            PageResult pageResult=questionService.findAllBase(queryPageBean);
           return new Result(true,"加载基础题库成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();

                return new Result(true,"加载基础题库失败");

        }

    }
    @RequestMapping("/addQuestion")
    public  Result addQuestion(@RequestBody Question question, HttpSession session){
        try {
            //获取提价的请求参数

            //手动设置却是的数据： status,reviewStatus,createDate,userId
            //发布状态
            question.setStatus(Constants.QUESTION_PRE_PUBLISH);
            //是否审核
            question.setReviewStatus(Constants.QUESTION_PRE_REVIEW);
            //添加时间
            question.setCreateDate(DateUtils.parseDate2String(new Date()));
            //从session里获取用户名并添加
            User user = (User) session.getAttribute(Constants.LOGIN_USER);
            question.setUserId(user.getId());
            questionService.addQuestion(question);
           return new Result(true,"新增试题成功");
        } catch (Exception e) {
            e.printStackTrace();

                return new Result(true,"新增试题失败");


        }


    }

}
