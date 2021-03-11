package com.itheima.mm.dao;

import com.itheima.mm.pojo.Catalog;

import java.util.List;

/**
 * @Author YWC
 * @Date 2021/2/4 16:14
 */

public interface CatalogDao {
    long findCatalogByCourseId(int id);
    List<Catalog> findAllCatalogListByCourseId(int courseId);
}
