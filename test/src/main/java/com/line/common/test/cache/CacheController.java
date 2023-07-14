package com.line.common.test.cache;

//import com.line.common.cache.redis.bloom.BloomManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import org.redisson.api.RBloomFilter;

/**
 * @Author: yangcs
 * @Date: 2020/10/23 13:39
 * @Description:
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserCache userCache;

    @Autowired
//    private BloomFilterTestCache bloomFilterTestCache;

//    @RequestMapping("/test/1")
//    public Integer selectOneByTKMapper() {
//        RBloomFilter filter = BloomManager.getInstance().getBloomFilter(BFNameUtils.getBfName(BloomFilterTestCache.UUID));
//        System.out.println(filter.contains("2"));
//        System.out.println(filter.contains("33"));
//        return (Integer) redisTemplate.opsForValue().get("age");
//    }

    //
    @RequestMapping("/v1/usercache")
    @ResponseBody
    public UserDTO getuserCache() {
        return userCache.get("ycs");
    }

}
