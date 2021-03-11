package com.itheima.mm.constant;


/**
 * 问题常量
 */
public interface Constants {
    /**
     * 题目状态
     */
    int QUESTION_PRE_PUBLISH = 0;           // 待发布 0
    int QUESTION_PUBLISHED = 1;            // 已发布 1
    int QUESTION_PUBLISHED_OFFLINE = 2;   // 已下架 2

    /**
     * 题目审核状态
     */
    int QUESTION_PRE_REVIEW = 0;            // 待审核 0
    int QUESTION_REVIEWED = 1;            // 已审核 1
    int QUESTION_REJECT_REVIEW = 2;        // 已拒绝 2
    //定义一个字符串常量 登录页
    String LOGIN_USER="loginuser";
    //categoryKind	/questions/categorys/1/101	1 = TAG
    //2 = 企业
    //3 = 方向
    //categoryType	/questions/categorys/1/101
    // 101 = 刷题
    //201 = 错题本
    //202 = 我的练习
    //203 = 收藏题库
    String VIEW_QUESTION="101";
    String ERROR_QUESTION="201";
    int MY_QUESTION=202;
    int COLLECT_QUESTION=203;
    String TAG ="1" ;
    String COMPANY ="2";
}
