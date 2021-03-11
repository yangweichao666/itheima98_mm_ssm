package com.itheima.mm.dao;

import com.itheima.mm.pojo.Dict;
import com.itheima.mm.pojo.WxMember;

/**
 * @Author YWC
 * @Date 2021/2/8 20:39
 */
public interface WxMemberDao {
    WxMember wxMemberLogin(WxMember wxMember);

    void wxMemberAdd(WxMember wxMember);

    WxMember findById(Integer id);

    void update(WxMember wxMember);

    int findAnswerCount(WxMember wxMember);

    Dict findCityCategory(WxMember wxMember);

    String findLastCategoryTitle(WxMember wxMember);
}
