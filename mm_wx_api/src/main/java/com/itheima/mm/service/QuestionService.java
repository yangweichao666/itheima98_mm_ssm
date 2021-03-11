package com.itheima.mm.service;

import com.alibaba.fastjson.JSONArray;
import com.itheima.mm.constant.Constants;
import com.itheima.mm.dao.QuestionDao;
import com.itheima.mm.dao.WxMemberDao;
import com.itheima.mm.pojo.Question;
import com.itheima.mm.pojo.WxMember;
import com.itheima.mm.utils.IntegerUtils;
import com.itheima.mm.utils.JsonUtils;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/9 11:55
 */
public class QuestionService {

    public List<Map> findCategorys(Map parameterMap) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
        //获取获取客户端传入的categoryKind、categoryType
        String categoryKind = "" + parameterMap.get("categoryKind");
        String categoryType = "" + parameterMap.get("categoryType");
        List<Map> items = null;
        if (categoryType.equals(Constants.VIEW_QUESTION)) {
            //刷题
            if (categoryKind.equals(Constants.TAG)) {
                //技术点
                //调用dao层的方法
                items = questionDao.findTAG(parameterMap);

            } else if (categoryKind.equals(Constants.COMPANY)) {
                //企业
                //调用dao层的方法
                items = questionDao.findCompany(parameterMap);
            } else {
                //行业方向
            }
        } else {

        }
        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return items;
    }

    //查询所有题目信息
    public Map findQuestionList(Map parameterMap) throws Exception {

        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);

        //获取获取客户端传入的categoryKind 二级目录或 企业、categoryType
        String categoryKind = "" + parameterMap.get("categoryKind");
        String categoryType = "" + parameterMap.get("categoryType");

        Map resultMap = new HashMap();

        List<Map> items = null;
        if (categoryType.equals(Constants.VIEW_QUESTION)) {
            //刷题
            //调用dao层的方法
            // 查询当前二级目录题目总数总数
            long allCount = questionDao.findAllCount(parameterMap);


                resultMap.put("allCount", allCount);

                //2.查询当前二级目录的所有题
                List<Question> questionList = questionDao.findQuestionListByCategoryId(parameterMap);
                resultMap.put("items", questionList);


               //3.查询最后一道题目的id
               int listQuestionId =questionDao.findLastQuestionId(parameterMap);
               resultMap.put("lastID", listQuestionId);
               SqlSessionFactoryUtils.commitAndClose(sqlSession);



        }


        return resultMap;
    }

    public void commitAnswer(Map parameterMap)  {
        //往tr_member_question中保存用户的做题记录：memberId 、question、isFavorite、answerResult、tag（只有tag 不在）
        //对于tag的分析：如果是选择题：true就是0、false就是1 ：如果是简答题，true是2、false就是3

        //怎么判断是选择题还是简答题 ？看answerResult是否为空

        SqlSession sqlSession = null;
        try {
            //第一步判断题目类型是简单还是选择题
            JSONArray jsonArray = (JSONArray) parameterMap.get("answerResult");
//        String[] answerResult = jsonArray.toArray()
            Boolean answerIsRight = (Boolean) parameterMap.get("answerIsRight");
            if (jsonArray == null) {
                //简答
                //咋判断answerIsRight为true或者是false,设置tag的值 为2或3
                if (answerIsRight == true) {
                    parameterMap.put("tag", 2);
                } else {
                    parameterMap.put("tag", 3);
                }
            } else {
                //选择题
                //咋判断answerIsRight为true或者是false,设置tag的值 为0或1
                if (answerIsRight) {
                    parameterMap.put("tag", 1);
                } else {
                    parameterMap.put("tag", 2);
                }

                //将answerResult转换成数组
                Object[] objects = jsonArray.toArray();
                //将其转换为字符串
                String answerResult = Arrays.toString(objects);
                //替换 map 中原本的 answerResult
                parameterMap.put("answerResult", answerResult);

            }
            //转换成isFavorite
            Boolean isFavorite = (Boolean) parameterMap.get("isFavorite");
            //判断用户是否收藏 并添加
            if (isFavorite) {
                parameterMap.put("isFavorite", 1);

            } else {
                parameterMap.put("isFavorite", 0);

            }

            //第二部：判断用户是否做过这道题：根据用户memberId到tr_member_question查询
            sqlSession = SqlSessionFactoryUtils.openSqlSession();
            QuestionDao questionDao = sqlSession.getMapper(QuestionDao.class);
            Map resultMap = questionDao.findByMemberIdAndQuestionId(parameterMap);
            if (resultMap != null) {
                //已将做过修改
                questionDao.updateMemberQuestion(parameterMap);
            } else {
                //没有做过 ，则新增
                questionDao.addMemberQuestion(parameterMap);
            }

            //第三步保存t_wx_member用户的lastxx的信息
            WxMemberDao wxMemberDao = sqlSession.getMapper(WxMemberDao.class);
            //根据id获取微信用户信息
            WxMember wxMember = wxMemberDao.findById((Integer) parameterMap.get("memberId"));
            //设置lastQuestionId
            wxMember.setLastQuestionId((Integer) parameterMap.get("id"));

            Integer categoryId = IntegerUtils.parseInteger(parameterMap.get("categoryID"));
            //设置lastcategoryType
            Integer categoryType = IntegerUtils.parseInteger(parameterMap.get("categoryType"));
            //设置 lastCategoryKind
            Integer categoryKind = IntegerUtils.parseInteger(parameterMap.get("categoryKind"));

            wxMember.setLastCategoryId(categoryId);
            wxMember.setLastCategoryKind(categoryKind);
            wxMember.setLastCategoryType(categoryType);

            wxMemberDao.update(wxMember);
            SqlSessionFactoryUtils.commitAndClose(sqlSession);
        } catch (Exception e) {
            e.printStackTrace();
            SqlSessionFactoryUtils.rollbackAndClose(sqlSession);
            //上抛运行时异常
            throw new RuntimeException(e.getMessage());
        }
    }
}
