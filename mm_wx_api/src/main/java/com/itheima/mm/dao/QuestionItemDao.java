package com.itheima.mm.dao;

import com.itheima.mm.pojo.QuestionItem;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/21 17:09
 */
public interface QuestionItemDao {
    //根据题目id 查询选项

    List<QuestionItem> findQuestionItemListByQuestionId(Integer questionId);
}
