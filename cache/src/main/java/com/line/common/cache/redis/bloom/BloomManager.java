//package com.line.common.cache.redis.bloom;
//
//
//import com.line.common.cache.redis.bloom.exception.BloomConfigException;
//import org.redisson.api.RBloomFilter;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//
///**
// * @Description 布隆过滤器注册 统一管理类，
// */
//public final class BloomManager {
//    /**
//     * 保存所有布隆过滤器实例
//     */
//    private final Map<String, RBloomFilter> bloomsMap = new ConcurrentHashMap();
//
//    private static final BloomManager INSTANCE = new BloomManager();
//    public static BloomManager getInstance() {
//        return INSTANCE;
//    }
//
//    /**
//     * 将缓存注册到BloomManager中，在各个布隆过滤器中调用此方法实现自主注册
//     */
//    public void register(RBloomFilter bf) {
//        // 不允许UUID重复，应用必须在实现的Cache接口确保命名不重复
//        String bfName = bf.getName();
//        if (bloomsMap.containsKey(bfName)) {
//            throw new BloomConfigException("Dumplicate bfName " + bfName
//                    + " to bloom provider " + bf.getName()
//                    + " and " + bloomsMap.get(bfName).getName());
//        }
//        bloomsMap.put(bfName, bf);
//    }
//
//    /**
//     *  该Id是否被注册?
//     */
//    public boolean isRegister(String bfName) {
//        return bloomsMap.containsKey(bfName);
//    }
//
//
////    /**
////     * 移除已注册的缓存
////     */
////    public RBloomFilter unRegister(String bfName) {
////        if (bloomsMap.containsKey(bfName)) {
////            return bloomsMap.remove(bfName);
////        }
////        return null;
////    }
//
//    public RBloomFilter getBloomFilter(String bfName) {
//        RBloomFilter bloom = bloomsMap.get(bfName);
//        if (bloom == null) {
//            throw new BloomConfigException(
//                    "No register bloom provider for  bfName " + bfName);
//        }
//        return bloom;
//    }
//
//    /**
//     * 清除所有管理的布隆过滤器
//     */
//    public void shutdown() {
//        bloomsMap.clear();
//    }
//
//
//    /**
//     * 禁止从外部拿到实例
//     * 创建一个新的实例 .
//     */
//    private BloomManager() {
//    }
//
//}