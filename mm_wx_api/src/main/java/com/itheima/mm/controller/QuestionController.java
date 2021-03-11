package com.itheima.mm.controller;

import com.itheima.framwork.anno.Controller;
import com.itheima.framwork.anno.RequestMapping;
import com.itheima.mm.entry.Result;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.service.QuestionService;
import com.itheima.mm.service.WxMemberService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/9 11:40
 */
@Controller
public class QuestionController {
    private WxMemberService wxMemberService = new WxMemberService();
    private QuestionService questionService = new QuestionService();

    @RequestMapping("/question/categorys")
    public void categorys(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1.获取用户id
            //获取请求头参数
            String authorization = request.getHeader("Authorization");
            //从authorization中获取yonghuid
            Integer id = Integer.valueOf(authorization.substring(7));
            // 获取用户信息
            WxMember wxMember = wxMemberService.findById(id);
            //2.获取请求参数

            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            parameterMap.put("memberId", wxMember.getId());
            parameterMap.put("memberCityId", wxMember.getCityId());
            parameterMap.put("memberCourseId", wxMember.getCourseId());
            List<Map> categoryList = questionService.findCategorys(parameterMap);

            JsonUtils.printResult(response, new Result(true, "获取题库列表成功", categoryList));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(true, "获取题库列表失败 "));

        }


    }

    //题目详情信息展示
    @RequestMapping("/question/questions")
    public void questions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //获取用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));

            //获取对象请求参数

            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);

            parameterMap.put("memberId", id);
            Map resultMap = questionService.findQuestionList(parameterMap);

            JsonUtils.printResult(response, new Result(true, "获取题目列表成功", resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false, "获取题目列表失败"));

        }


    }
    @RequestMapping("/question/commit")
    public void commitAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //1.获取请求参数
            Map parameterMap=JsonUtils.parseJSON2Object(request, Map.class);
            //2.获取用户id
            String authorization = request.getHeader("Authorization");
            Integer id = Integer.valueOf(authorization.substring(7));
            //3.将memberId存储到parameterMap中
            parameterMap.put("memberId", id);
            //4.调用业务从的方法 提交做题信息
            questionService.commitAnswer(parameterMap);
            JsonUtils.printResult(response, new Result(true,"保存做题记录提交成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(true,"保存做题记录提交失败"));

        }


    }
}
