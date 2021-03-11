package com.itheima.mm.dao;

import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/21 17:22
 */
public interface TagDao {
    //外部查询 查询此学科的标签
    List<Tag> findTagListQuestionId(Integer questionId);
}
