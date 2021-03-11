package com.itheima.mm.dao;

import com.itheima.mm.pojo.Tag;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/4 16:25
 */
public interface TagDao {
    long findTagByCourseId(int id);
    List<Tag> findTagNameByCourseId(int courseId);
}
