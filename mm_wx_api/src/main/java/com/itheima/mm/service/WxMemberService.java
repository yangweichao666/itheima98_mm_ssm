package com.itheima.mm.service;

import com.itheima.mm.dao.WxMemberDao;

import com.itheima.mm.pojo.Dict;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.JsonUtils;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.internal.builders.JUnit3Builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/8 20:09
 */
public class WxMemberService {

    public WxMember wxMemberFind(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
        //调用业务从的方法
        WxMember wxMemberLogin= wxMemberDao.wxMemberLogin(wxMember);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return wxMemberLogin;


    }

    public void wxMemberAdd(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
        //调用业务从的方法
        wxMemberDao.wxMemberAdd(wxMember);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);

    }

    public WxMember findById(Integer id) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
        WxMember wxMember=wxMemberDao.findById(id);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return wxMember;
    }

    public void update(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
        wxMemberDao.update(wxMember);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
    }

    public Map findCenterInfo(WxMember wxMember) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
        //定义一个符合接口的map对象
        Map resultMap=new HashMap();
        //1.获取用户答题数
        int answerCount=wxMemberDao.findAnswerCount(wxMember);
        //2.获取城市学科
        Dict dict=wxMemberDao.findCityCategory(wxMember);
        Map category=new HashMap();
        category.put("cityId",wxMember.getCityId());
        category.put("cityName", dict.getDataValue());
        category.put("subjectID", wxMember.getCourseId());
        //3.获取最后答题信息
        Map lastAnswer=new HashMap();
        lastAnswer.put("categoryID", wxMember.getLastCategoryId());
        lastAnswer.put("categoryType", wxMember.getLastCategoryType());
        lastAnswer.put("categoryKind",wxMember.getLastCategoryKind());
        //单独查询分类标题
        String lastCategoryTitle = wxMemberDao.findLastCategoryTitle(wxMember);
        lastAnswer.put("categoryTitle",lastCategoryTitle);



        //封装
        resultMap.put("answerCount", answerCount);
        resultMap.put("lastAnswer", lastAnswer);
        resultMap.put("category", category);

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return resultMap;
    }
}
