package com.itheima.mm.service;

import com.itheima.mm.dao.CityDao;
import com.itheima.mm.pojo.Dict;
import com.itheima.mm.utils.LocationUtil;
import com.itheima.mm.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author YWC
 * @Date 2021/2/8 14:36
 */
public class CityService {


    public Map findCityService(Map parameterMap) throws Exception {
        //1.获取客户传入的经纬度
        String location = (String)parameterMap.get("location");
        //2.调用高德地图上的api根据经纬度，获取当前城市
        String cityName = LocationUtil.parseLocation(location);
        //调用dao层的方法，根据城市名，查询到城市信息（id ,title）;
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        CityDao cityDao = sqlSession.getMapper(CityDao.class);
        //当前城市信息，包含id和title
        Dict currentCity = cityDao.findByName(cityName);
//        System.out.println(currentCity);
        //根据fs查询是否是首屏推荐城市0 全部 1 首屏推荐
        String fs = ""+parameterMap.get("fs");
        List<Dict> cityList=cityDao.findByRecommendName(fs);
        //将数据放入map
        Map resultMap=new HashMap();
        resultMap.put("location", currentCity);
        resultMap.put("citys", cityList);
        SqlSessionFactoryUtils.commitAndClose(sqlSession);

        return resultMap;
    }
}
