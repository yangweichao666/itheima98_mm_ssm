package com.itheima.mm.controller;

import com.itheima.framwork.anno.Controller;
import com.itheima.framwork.anno.RequestMapping;
import com.itheima.mm.entry.Result;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.service.WxMemberService;
import com.itheima.mm.utils.DateUtils;
import com.itheima.mm.utils.JsonUtils;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/8 19:49
 */
@Controller
public class WxMemberController {
    private WxMemberService wxMemberService=new WxMemberService();
    @RequestMapping("/wxMember/login")
    public void  wxMemberLogin(HttpServletRequest request, HttpServletResponse response){

        try {
            //1获取请求参数
            WxMember wxMember = JsonUtils.parseJSON2Object(request, WxMember.class);
            //2判断用户名是否在面面服务器中注册过
            System.out.println(wxMember);
            WxMember wxMemberFind=wxMemberService.wxMemberFind(wxMember);
            if (wxMemberFind==null){
                //说明没有注册过
                //我们要事先设置好相应的创建时间 并进行了注册
                wxMember.setCreateTime(DateUtils.parseDate2String(new Date()));
                wxMemberService.wxMemberAdd(wxMember);

            }else {
//                因为要返回Wxmemberid
                wxMember=wxMemberFind;
            }
            //响应给客户端
            Map resultMap=new HashMap();
            resultMap.put("token", wxMember.getId());
            resultMap.put("userInfo", wxMember);
            JsonUtils.printResult(response, new Result(true,"登录成功",resultMap));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @SneakyThrows
    @RequestMapping("/WxMember/setCityCourse")
    public void  setCityCourse(HttpServletRequest request, HttpServletResponse response){
        try {
            //获取求求参数
            Map mapCityCourse = JsonUtils.parseJSON2Object(request, Map.class);
            System.out.println(mapCityCourse);
            //获取请求头参数
            String authorization = request.getHeader("Authorization");
            System.out.println(authorization);
            //从authorization中获取yonghuid
            Integer id =Integer.valueOf(authorization.substring(7));
//            调用业务层方法获取id
           WxMember wxMember= wxMemberService.findById(id);
           //5.最终目的将城市和学科绑定设置到wxMember放到数据库中

//            String cityID=""+mapCityCourse.get("cityID");
            Integer cityId=null;
            if (mapCityCourse.get("cityID") instanceof Integer){
                cityId=(Integer)mapCityCourse.get("cityID");
            }else {
                cityId=Integer.valueOf((String)mapCityCourse.get("cityID"));
            }
            Integer subjectId=null;
            if (mapCityCourse.get("subjectID") instanceof Integer){
                subjectId=(Integer)mapCityCourse.get("subjectID");
            }else {
                subjectId=Integer.valueOf((String)mapCityCourse.get("subjectID"));
            }
            wxMember.setCityId(cityId);
            wxMember.setCourseId(subjectId);
            wxMemberService.update(wxMember);
            JsonUtils.printResult(response, new Result(true,"添加方向成功"));
        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(true,"添加方向失败"));

        }

    }
    @RequestMapping("/WxMember/Center")
    public void center(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String authorization = request.getHeader("Authorization");
            Integer id =Integer.valueOf(authorization.substring(7));
//            1.调用业务层方法获取id
            WxMember wxMember = wxMemberService.findById(id);
            //2.调用业务层方法查询响应数据
            Map resultMap=wxMemberService.findCenterInfo(wxMember);
            //3想客户端响应数据
             JsonUtils.printResult(response, new Result(true,"获取个人用户信息成功",resultMap));

        } catch (Exception e) {
            e.printStackTrace();
            JsonUtils.printResult(response, new Result(false,"获取个人用户信息失败"));

        }

    }
}
