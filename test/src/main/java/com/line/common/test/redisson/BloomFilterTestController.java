//package com.line.common.test.redisson;
//
////import org.redisson.api.RBloomFilter;
////import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @Author: yangcs
// * @Date: 2020/12/2 14:43
// * @Description:
// */
//@Controller
//@RequestMapping("/bf")
//public class BloomFilterTestController {
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @RequestMapping("/c")
//    @ResponseBody
//    public String tc() {
//        RBloomFilter<Integer> c1 = redissonClient.getBloomFilter("c2");
//        System.out.println(c1.isExists());
//        c1.tryInit(10L, 0.1);
//        Integer a = 1;
//        System.out.println(c1.isExists());
//        c1.add(a);
//        System.out.println(c1.isExists());
//        c1.delete();
//        c1.delete();
//        c1.delete();
//        c1.delete();
//        System.out.println(c1.isExists());
//        return "OK";
//    }
//
//
//    @RequestMapping("/rc")
//    @ResponseBody
//    public String rtc() {
//        RBloomFilter<Object> cc1 = redissonClient.getBloomFilter("cc1");
//        cc1.tryInit(100L, 0.1);
//        cc1.add(123);
//        System.out.println(cc1.count()+"==="+cc1.contains(123));
//        cc1.tryInit(300L, 0.25);
//        System.out.println(cc1.count()+"==="+cc1.contains(123));
//        cc1.tryInit(301L, 0.15);
//        System.out.println(cc1.count()+"==="+cc1.contains(123));
//        return "OK";
//    }
//}
