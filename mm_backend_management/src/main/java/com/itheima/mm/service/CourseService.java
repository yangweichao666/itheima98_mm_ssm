package com.itheima.mm.service;

import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Course;

import java.io.IOException;
import java.util.List;

/**
 * @Author YWC
 * @Date 2021/3/6 20:41
 */
public interface CourseService {
    void add(Course course) throws IOException;

    PageResult findByPage(QueryPageBean queryPageBean) throws Exception;

    void update(Course course) throws Exception;

    void delete(int id) throws Exception;

    List<Course> findAll(String status) throws Exception;
}
