package com.line.common.cache.redis.bloom;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/11/27 14:19
 * @Description: 布隆过滤器 基于redisson实现
 * bloom过滤器基础bitmaps,但因为存在hash冲突, bloom会错报(不存在的判断为 存在),但绝不会漏报(存在的判断为 不存在)
 *
 * 默认一个过滤器 过滤,十万元素,误差0.03
 */

public abstract class AbstractBloomFilter<E> implements IBloomFilter<E>, InitializingBean {

    //TODO  APP满23456
    // bloom过滤器
    private RBloomFilter<E> bloomFilter;

    private static final Logger logger = LoggerFactory.getLogger(AbstractBloomFilter.class);

    /**
     * @param e 元素
     * @return 成功或失败
     * @description 新增元素
     */
    @Override
    public boolean add(E e) {
        ifnull(e);
        return bloomFilter.add(e);
    }

    /**
     * @param e 元素
     * @return true 存在, false 不存在
     * @description 判断单个元素是否 存在Bloom过滤器中
     */
    @Override
    public boolean contains(E e) {
        ifnull(e);
        return bloomFilter.contains(e);
    }

    /**
     * @param es 一组元素
     * @return true 这一组元素都存在, false 这一组数据中有不存在的
     * @description 判断一组元素是否 全部都存在Bloom过滤器中
     */
    @Override
    public boolean containsAll(E... es) {
        for (E e : es) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param es 一组元素
     * @return 过滤出有效的数据
     * @description 从一组元素中过滤出有效的元素
     */
    @Override
    public List<E> filterActive(E... es) {
        List<E> eList = new ArrayList();
        for (E e : es) {
            if (contains(e)) {
                eList.add(e);
            }
        }
        return eList;
    }

    private void ifnull(E e) {
        if (null == e) {
            throw new NullPointerException("元素E 为null!");
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        //尝试到远程过滤器服务注册
        logger.info(">>>>>>>> 尝试到远程过滤器服务注册");
        BFInitParam bfInfo = getBfInfo();


        //设置使用的过滤器
        this.bloomFilter = null;
        //注册当前布隆过滤器,防止重复配置
//        BloomManager.getInstance().register();
    }


    public abstract BFInitParam getBfInfo();
}
