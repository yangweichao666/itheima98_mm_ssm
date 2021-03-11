package com.itheima.mm.dao;

import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/4 16:29
 */
public interface QuestionDao {
    long findQuestionByCourseId(int id);

    long findAllBaseTotal(QueryPageBean queryPageBean);

    List<Question> findAllBaseList(QueryPageBean queryPageBean);

    void add(Question question);
//添加题目id和标签id
    void addQuestionTag(Map paramMap);
}
