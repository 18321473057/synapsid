package com.line.common.bloomfiltermanage.manage;


import com.line.common.cache.redis.bloom.exception.BloomConfigException;
import org.redisson.api.RBloomFilter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Description 布隆过滤器注册 统一管理类，可以通过各个布隆过滤器的name进行获取
 */
public final class BloomRegisterManager {

    private static final BloomRegisterManager INSTANCE = new BloomRegisterManager();

    /**
     * 保存所有布隆过滤器实例
     */
    private final Map<String, RBloomFilter> bloomsMap = new ConcurrentHashMap();


    public static BloomRegisterManager getInstance() {
        return INSTANCE;
    }

    /**
     * 将缓存注册到BloomManager中，在各个布隆过滤器中调用此方法实现自主注册
     */
    public void register(RBloomFilter bf) {
        // 不允许UUID重复，应用必须在实现的Cache接口确保命名不重复
        String bfName = bf.getName();
        if (bloomsMap.containsKey(bfName)) {
            throw new BloomConfigException("Dumplicate bfName " + bfName
                    + " to bloom provider " + bf.getName()
                    + " and " + bloomsMap.get(bfName).getName());
        }
        bloomsMap.put(bfName, bf);
    }

    /**
     *  该Id是否被注册?
     */
    public boolean isRegister(String bfName) {
        return bloomsMap.containsKey(bfName);
    }


    /**
     * 移除已注册的缓存
     */
    public RBloomFilter unRegister(String bfName) {
        if (bloomsMap.containsKey(bfName)) {
            return bloomsMap.remove(bfName);
        }
        return null;
    }

    /**
     * 根据uuid获取布隆过滤器
     */
    public RBloomFilter getBloomFilter(String bfName) {
        RBloomFilter bloom = bloomsMap.get(bfName);
        if (bloom == null) {
            throw new BloomConfigException(
                    "No register bloom provider for  bfName " + bfName);
        }
        return bloom;
    }

    /**
     * 清除所有管理的布隆过滤器
     */
    public void shutdown() {
        bloomsMap.clear();
    }


    /**
     * 禁止从外部拿到实例
     * 创建一个新的实例 .
     */
    private BloomRegisterManager() {
    }

}