package com.itheima.mm.controller;

import com.itheima.framwork.anno.Controller;
import com.itheima.framwork.anno.RequestMapping;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.service.CityService;
import com.itheima.mm.service.CourseService;
import com.itheima.mm.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itheima.mm.entry.Result;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/8 12:26
 */
@Controller
public class CommonController {

    @RequestMapping("/common/cities")
    public   void cities(HttpServletRequest request, HttpServletResponse response){
        CityService cityService=new CityService();
        try {
            //获取请求参数
            Map parameterMap = JsonUtils.parseJSON2Object(request, Map.class);
            //调用方法获取相应的数据
           Map resultMap= cityService.findCityService(parameterMap);

           //想客户端响应数据
            JsonUtils.printResult(response, new Result(true,"查询城市成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                JsonUtils.printResult(response, new Result(true,"查询城市失败"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

    }
    @RequestMapping("/common/courseList")
    public void  courseList(HttpServletRequest request,HttpServletResponse response){

        try {
            CourseService courseService = new CourseService();
        //盗用CourseService的方法查询所有学科
        List<Course> courseList= courseService.findAll();
//        将查询到的所有学科封装并相应数据

            JsonUtils.printResult(response, new Result(true,"查询成功",courseList) );
        } catch (Exception e) {
            e.printStackTrace();
            try {
                JsonUtils.printResult(response, new Result(true,"查寻失败") );
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }
}
