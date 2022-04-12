//package com.line.common.cache.redis.bloom;
//
//import com.line.base.core.dto.AjaxResponseDto;
//import com.line.base.core.shutdown.ShowDownUtils;
//import com.line.common.cache.redis.bloom.exception.BloomConfigException;
//import com.line.common.cache.redis.bloom.remote.BloomFRemote;
//import org.redisson.api.RBloomFilter;
//import org.redisson.api.RedissonClient;
//
///**
// * @Author: yangcs
// * @Date: 2020/12/7 13:52
// * @Description:
// */
//public class BloomFilterIniter {
//
//    private ShowDownUtils showDownUtils;
//
//    private BloomFRemote bloomFRemote;
//
//    private RedissonClient redissonClient;
//
//    public RBloomFilter initBloomFilter(String redisUUID) {
//        //先配置 过滤器
//        AjaxResponseDto<String> ajax = bloomFRemote.getBloomFilter(redisUUID);
//        if (!ajax.getSuccess() || ajax.getObj() == null) {
//            showDownUtils.stop(new BloomConfigException("初始化名为" + redisUUID + "的redis服务的布隆过滤器失败!"));
//        }
//        BloomManager.getInstance().register(redissonClient.getBloomFilter(ajax.getObj()));
//        return BloomManager.getInstance().getBloomFilter(ajax.getObj());
//    }
//
//
//    public void setShowDownUtils(ShowDownUtils showDownUtils) {
//        this.showDownUtils = showDownUtils;
//    }
//
//
//    public void setBloomFRemote(BloomFRemote bloomFRemote) {
//        this.bloomFRemote = bloomFRemote;
//    }
//
//    public void setRedissonClient(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }
//}
