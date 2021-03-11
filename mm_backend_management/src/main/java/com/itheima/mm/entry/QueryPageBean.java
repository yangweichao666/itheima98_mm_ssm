package com.itheima.mm.entry;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：yp
 * @description : 封装分页查询条件
 * @version: 1.0
 */
@Data
public class QueryPageBean implements Serializable{
    private Integer currentPage; // 页码
    private Integer pageSize;   //每页记录数
    private Map queryParams;    //查询条件

    //简化dao层的代码  用于dao层进行分页时要跳过的数据条数
    private Integer offset; // 分页查询，开始记录下标

    /**
     * 获取分页起始记录位置
     * 根据分页页数，计算limit其实记录
     * @return
     */
    public Integer getOffset(){
        return (currentPage-1)*pageSize;
    }
}