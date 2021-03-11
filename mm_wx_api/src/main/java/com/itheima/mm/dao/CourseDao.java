package com.itheima.mm.dao;

import com.itheima.mm.pojo.Course;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/8 17:35
 */
public interface CourseDao {
    List<Course> findAll();
}
