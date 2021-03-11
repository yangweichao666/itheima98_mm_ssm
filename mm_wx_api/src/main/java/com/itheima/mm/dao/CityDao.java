package com.itheima.mm.dao;

import com.itheima.mm.pojo.Dict;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/8 15:06
 */
public interface CityDao {
   Dict findByName(String cityName);

   List<Dict> findByRecommendName(String fs);
}
