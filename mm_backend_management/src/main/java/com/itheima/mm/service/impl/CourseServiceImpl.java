package com.itheima.mm.service.impl;

import com.itheima.mm.dao.CourseDao;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.TagDao;
import com.itheima.mm.dao.CatalogDao;
import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Course;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Author YWC
 * @Date 2021/2/3 11:35
 */
@Service
public class CourseServiceImpl  implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private CatalogDao catalogDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private QuestionDao questionDao;
    @Override
    public void add(Course course) throws IOException {
        courseDao.add(course);

    }
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) throws Exception {
        //1.查询总数据条数
        Long totalCount = courseDao.findTotalCount(queryPageBean);
        //2.查询当前页数据
        List<Course>  listPage=courseDao.findPageList(queryPageBean);
        //将查询到的PageResult对象返回
      return new PageResult(totalCount,listPage);
    }
    @Override
    @Transactional
    public void update(Course course) throws Exception {

        courseDao.update(course);
    }
    @Override
    @Transactional
    public void delete(int id) throws Exception {

        //查询是否与二级目录有关联
       long  count=catalogDao.findCatalogByCourseId(id);
        if (count > 0) {
            throw new TimeoutException("与二级目录有关联不能删除");
        }
        //判断当前要删除的学科是否有关联的标签
           count =tagDao.findTagByCourseId(id);
        if (count > 0) {
            throw new TimeoutException("与二级目录有关联不能删除");
        }
        //判断当前要删除的学科是否与题目有关联
        count= questionDao.findQuestionByCourseId(id);
        if (count>0){
            throw new TimeoutException("与题目有关联不能删除");
        }

        courseDao.delete(id);

    }
    @Override
    public List<Course> findAll(String status) throws Exception {

        List<Course> courseList= courseDao.findAll(status);

        return courseList;
    }
}
