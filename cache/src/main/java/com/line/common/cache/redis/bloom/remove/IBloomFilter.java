//package com.line.common.cache.redis.bloom;
//
//import java.util.List;
//
///**
// * @Author: yangcs
// * @Date: 2020/11/28 9:11
// * @Description: 布隆过滤器接口
// */
//public interface IBloomFilter<E> {
//
//    /**
//     * @description 标记当前Bloom的UUID
//     */
//    String getUUID();
//
//    /**
//     * @description  新增元素
//     * @param e 元素
//     * @return 成功或失败
//     */
//    boolean add(E e);
//
//    /**
//     * @description 判断单个元素是否 存在Bloom过滤器中
//     * @param e 元素
//     * @return true 存在, false 不存在
//     */
//    boolean contains(E e);
//
//    /**
//     * @description 判断一组元素是否 全部都存在Bloom过滤器中
//     * @param  es 一组元素
//     * @return true 这一组元素都存在, false 这一组数据中有不存在的
//     */
//    boolean containsAll(E... es);
//
//    /**
//     * @description 从一组元素中过滤出有效的元素
//     * @param  es 一组元素
//     * @return 过滤出有效的数据
//     */
//    List<E> filterActive(E... es);
//
//}
