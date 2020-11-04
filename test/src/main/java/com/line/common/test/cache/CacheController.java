package com.line.common.test.cache;

import com.line.common.test.mybatis.po.TestPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 通用mapper
     */
    @RequestMapping("/test/1")
    public Integer selectOneByTKMapper() {
        return (Integer) redisTemplate.opsForValue().get("age");
    }

}
