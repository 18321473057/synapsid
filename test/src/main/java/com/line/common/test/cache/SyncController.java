//package com.line.common.test.cache;
//
////import org.redisson.api.RLock;
////import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author: yangcs
// * @Date: 2020/11/23 14:17
// * @Description:
// */
//@Controller
//@RequestMapping("sync")
//public class SyncController {
//    @Autowired
//    private RedissonClient redisson;
//
//
//    @RequestMapping("zr")
//    @ResponseBody
//    public String zr() throws InterruptedException {
//        RLock zrlock = redisson.getLock("zrlock");
//        //判断是否有锁
//        boolean locked1 = zrlock.isLocked();
//        //加锁
//        zrlock.lock(60, TimeUnit.SECONDS);
//        boolean locked = zrlock.isLocked();
//        //释放锁
//        zrlock.unlock();
//        boolean lock1ed = zrlock.isLocked();
//        //再入锁,10s等待,60s有效时间,不能自动续时
//        zrlock.tryLock(10, 60, TimeUnit.SECONDS);
//        //再试加锁,会让锁+1,默认设置30s
//        zrlock.tryLock();
//
//        //trylock两次,也需要连续解锁两次才可以
//        boolean ldock1ed = zrlock.isLocked();
//        zrlock.unlock();
//        boolean locker3d = zrlock.isLocked();
//        zrlock.unlock();
//        boolean locker13d = zrlock.isLocked();
//        return "OK";
//    }
//
//    @RequestMapping("trzr")
//    @ResponseBody
//    public String trzr() throws InterruptedException {
//        RLock zrlock = redisson.getLock("trzrlock");
//        boolean b = zrlock.tryLock(1, 60, TimeUnit.SECONDS);
//        zrlock.tryLock();
//        return "OK";
//    }
//}
