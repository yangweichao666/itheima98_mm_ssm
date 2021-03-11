package com.itheima.mm.dao;

import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Course;



import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/3 11:40
 */
public interface CourseDao {
    void add(Course course);

    Long findTotalCount(QueryPageBean queryPageBean);

    List<Course> findPageList(QueryPageBean queryPageBean);

    void update(Course course);

    void delete(int id);

//    List<Course> findAll(@Param("status")String status);
    List<Course> findAll(String status);


}
