package com.itheima.mm.service;

import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/8 17:26
 */
public class CourseService {
   public List<Course> findAll() throws Exception {
       SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
       CourseDao courseDao = sqlSession.getMapper(CourseDao.class);
//       调用业务层方法
       List<Course> courseList=courseDao.findAll();
       SqlSessionFactoryUtils.commitAndClose(sqlSession);
       return courseList;
   }
}
