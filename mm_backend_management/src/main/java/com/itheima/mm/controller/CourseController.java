package com.itheima.mm.controller;


import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.entry.Result;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.User;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.service.impl.CourseServiceImpl;
import com.itheima.mm.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.itheima.mm.constant.Constants.LOGIN_USER;

/**
 * @Author YWC
 * @Date 2021/2/3 10:56
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
   @RequestMapping("/add")
    public Result add(@RequestBody Course course, HttpSession session) throws IOException {
       try {
//
           //补全Course的其余参数
           User user = (User)session.getAttribute(LOGIN_USER);
           //设置userId
           course.setUserId(user.getId());
           //设置修改时间
           course.setCreateDate(DateUtils.parseDate2String(new Date()));
           course.setOrderNo(2);
           //调用业务层的方法保存course
           courseService.add(course);
           return new Result(true,"学科插入成功");

       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,"添加失败");
       }

   }
    @RequestMapping("/findByPage")
   public Result  findByPage(@RequestBody QueryPageBean queryPageBean){
        try {
//            //获取请求参数，并封装
//            QueryPageBean queryPageBean = JsonUtils.parseJSON2Object(request, QueryPageBean.class);
            //调用业务层方法获取pageRequest对象
            PageResult pageResult =courseService.findByPage(queryPageBean);


            return new Result(true,"查询成功",pageResult);
        } catch (Exception e) {
            e.printStackTrace();

                return new Result(true,"查询失败");



        }

    }

    /**
     * 修改学科
     *
     */
    @RequestMapping("/update")
    public  Result  update(@RequestBody Course course,HttpServletResponse response){
        try {

            System.out.println(course);
            courseService.update(course);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();

                return new Result(false,"修改学科失败");

        }

    }

    @RequestMapping("/delete")
    public Result delete(int id  ) {
        try {

            System.out.println(id);
            //调用业务层的方法根据id删除学科信息
            courseService.delete(id);
            //删除成功
           return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();

                return new Result(false,e.getMessage());

        }

    }
    @RequestMapping("/findAll")
    public Result findAll(String status) {
        try {

            List<Course> courses= courseService.findAll(status);
            return new Result(true,"获取所有学科成功",courses);

        } catch (Exception e) {
            e.printStackTrace();

               return new Result(false ,"获取所有学科失败");

        }
    }
}
