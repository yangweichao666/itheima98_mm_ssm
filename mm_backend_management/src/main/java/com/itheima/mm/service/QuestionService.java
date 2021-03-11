package com.itheima.mm.service;

import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Question;

/**
 * @Author YWC
 * @Date 2021/3/6 20:39
 */
public interface QuestionService {
    PageResult findAllBase(QueryPageBean queryPageBean) throws Exception;

    void addQuestion(Question question);
}
