package com.itheima.mm.dao;

import com.itheima.mm.pojo.Question;

import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/9 15:08
 */
public interface QuestionDao {
    List<Map> findTAG(Map parameterMap);

    List<Map> findCompany(Map parameterMap);

    long findAllCount(Map parameterMap);

    List<Question> findQuestionListByCategoryId(Map parameterMap);

    int findLastQuestionId(Map parameterMap);
//根基根据用户memberId到tr_member_question查询做题记录
    Map findByMemberIdAndQuestionId(Map parameterMap);

    void updateMemberQuestion(Map parameterMap);

    void addMemberQuestion(Map parameterMap);
}
