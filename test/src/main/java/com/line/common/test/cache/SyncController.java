package com.line.common.test.cache;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Author: yangcs
 * @Date: 2020/11/23 14:17
 * @Description:
 */
@Controller
@RequestMapping("sync")
public class SyncController {
    @Autowired
    private RedissonClient redisson;



    @RequestMapping("zr")
    @ResponseBody
    public String  zr(){
        RLock zrlock = redisson.getLock("zrlock");
        zrlock.lock(10, TimeUnit.SECONDS);
//        zrlock.unlock();
        return "OK";
    }

}
