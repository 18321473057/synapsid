package com.line.common.test.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/test/1")
    public Integer selectOneByTKMapper() {
        return (Integer) redisTemplate.opsForValue().get("age");
    }

    //
    @RequestMapping("/v1/usercache")
    @ResponseBody
    public UserDTO getuserCache() {
        return userCache.get("ycs");
    }

}
