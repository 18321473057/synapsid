package com.line.common.cache.redis.bloom;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Description 布隆过滤器 统一管理类，可以通过各个布隆过滤器的uuid进行获取
 */
public final class BloomManager {

    private static final BloomManager INSTANCE = new BloomManager();

    /**
     * 保存所有布隆过滤器实例
     */
    private final Map<String, IBloomFilter> uuidBlooms = new ConcurrentHashMap();

    /**
     * 禁止从外部拿到实例
     * 创建一个新的实例 .
     */
    private BloomManager() {
    }

    public static BloomManager getInstance() {
        return INSTANCE;
    }

    /**
     * 将缓存注册到BloomManager中，在各个布隆过滤器中调用此方法实现自主注册
     */
    public void registerCache(IBloomFilter cache) {
        // 不允许UUID重复，应用必须在实现的Cache接口确保命名不重复
        String uuid = cache.getUUID();
        if (uuidBlooms.containsKey(uuid)) {
            throw new BloomConfigException("Dumplicate uuid " + uuid
                    + " to bloom provider " + cache.getClass().getName()
                    + " and " + uuidBlooms.get(uuid).getClass().getName());
        }
        uuidBlooms.put(uuid, cache);
    }

    /**
     * 移除已注册的缓存
     */
    public IBloomFilter unRegisterCache(String uuid) {
        if (uuidBlooms.containsKey(uuid)) {
            return uuidBlooms.remove(uuid);
        }
        return null;
    }

    /**
     * 根据uuid获取布隆过滤器
     *
     */
    public IBloomFilter getBloomFilter(String uuid) {
        IBloomFilter bloom = uuidBlooms.get(uuid);
        if (bloom == null) {
            throw new BloomConfigException(
                    "No register bloom provider for  UUID " + uuid);
        }
        return bloom;
    }

    /**
     * 清除所有管理的布隆过滤器
     */
    public void shutdown() {
        uuidBlooms.clear();
    }
}