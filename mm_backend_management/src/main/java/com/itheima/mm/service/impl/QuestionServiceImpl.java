package com.itheima.mm.service.impl;

import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.QuestionItemDao;
import com.itheima.mm.entry.PageResult;
import com.itheima.mm.entry.QueryPageBean;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.QuestionItem;
import com.itheima.mm.pojo.Tag;
import com.itheima.mm.service.QuestionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/5 19:53
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private QuestionItemDao questionItemDao;
    @Override
    public PageResult findAllBase(QueryPageBean queryPageBean) throws Exception {


        //查询题目总条数
        long questiontotal = questionDao.findAllBaseTotal(queryPageBean);
        //获取当前题目数据集合

        List<Question> questionList = questionDao.findAllBaseList(queryPageBean);
        //查询成功将其封装

        PageResult pageResult = new PageResult(questiontotal, questionList);

        return pageResult;
    }
     @Override
     @Transactional
     public void addQuestion(Question question) {

            //1将question表现观数据存储到t_question表中
            questionDao.add(question);

            //2.判断选项集合是否为空，如果不为空则将题目的选项信息存储到t_question_item表中
            //选项集合
            List<QuestionItem> questionItemList = question.getQuestionItemList();

            //不为空并且长度大于0
            if (questionItemList != null && questionItemList.size() > 0) {
                for (QuestionItem questionItem : questionItemList) {
                    //手动设置  该选项的 在插入时返回的 题目id
                    questionItem.setQuestionId(question.getId());
                    //调用dao方法存储每一个题目的选项
                    questionItemDao.add(questionItem);

                }
            }
            //3.讲题目与标签关联信息存储到tr_question表中
            //获取该题目的所有标签
            //要传入题目的id和标签的id
            List<Tag> tagList = question.getTagList();
            if (tagList != null && tagList.size() > 0) {

                for (Tag tag : tagList) {
                    Map paramMap = new HashMap<>();
                    //进行关联
                    paramMap.put("questionId", question.getId());
                    paramMap.put("tagId", tag.getId());
                    questionDao.addQuestionTag(paramMap);

                }
            }


    }
}
