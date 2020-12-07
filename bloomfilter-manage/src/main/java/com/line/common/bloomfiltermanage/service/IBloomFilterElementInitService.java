package com.line.common.bloomfiltermanage.service;

import org.redisson.api.RBloomFilter;

import java.util.Set;
import java.util.function.Supplier;

/**
 * @Author: yangcs
 * @Date: 2020/12/4 13:29
 * @Description: 布隆过滤器 元素初始化 服务;
 * <p>
 * warning::  本接口只提供 单库数据 初始化 ; 分库情况本接口不提供
 */
public interface IBloomFilterElementInitService {


    /**
     * @param rf         过滤器
     * @param table      表名
     * @param fieldNames 字段名数组
     * @param joinChar   链接字符
     * @return 返回添加 的条数;
     * @description 将查询到的数据 添加到指定过滤器中
     * //TODO     * @exception  可能会者sql异常的情况,异常类后面写
     */
    long initElementByTable(RBloomFilter rf, String table, String[] fieldNames, String joinChar);


    /**
     * @param rf       过滤器
     * @param querySql 可查到数据的sql,
     * @description 将查询到的数据 添加到指定过滤器中
     * //TODO     * @exception  可能会者sql异常的情况,异常类后面写
     * @warning:: querySql 查询到的数据必须只能返回一列数据
     */
    long initElementBySql(RBloomFilter rf, String querySql);


    /**
     * @param rf       过滤器
     * @param supplier 过滤器元素提供者,
     * @description 将查询到的数据 添加到指定过滤器中
     */
    long initElementBySupplier(RBloomFilter rf, Supplier<Set<String>> supplier);


}
