//package com.line.common.bloomfiltermanage.controller;
//
//import com.line.base.web.response.annotation.AjaxResponse;
////import com.line.common.bloomfiltermanage.manage.BloomFilterDDLService;
//import com.line.common.cache.redis.bloom.BFInitParam;
//import com.line.common.cache.redis.utils.BFNameUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @Author: yangcs
// * @Date: 2020/12/4 10:07
// * @Description: 本控制器 负责 删除,新建以及重建 布隆过滤器; 重建过程中需要初始化过滤器
// * <p>
// * 本应该有一个管理界面, 这里没时间写; 省去吧直接接口调用吧;
// */
//
//@Controller
//@RequestMapping("/ddl/bf")
//public class
//DDLBloomFilterController {
//
////    @Autowired
////    private BloomFilterDDLService bloomFilterDDLService;
//
//
//    /**
//     * @param redisUUID redis uuid
//     * @return -1:没有查询到这个过滤器; 0:删除失败; >0 :删除的个数
//     */
//    @DeleteMapping("/del")
//    @AjaxResponse
//    public Integer delt(@RequestParam String redisUUID) {
//        return bloomFilterDDLService.delt(BFNameUtils.getBfName(redisUUID));
//    }
//
//    /**
//     * @param param 构建过滤器的参数对象
//     * @return 成功/失败
//     * @Description 强制创建 布隆过滤器,如果过滤器已经存在 则删除 重建
//     * 通过编写好的sql 初始化过滤器元素
//     */
//    @PostMapping("/re/create/sql")
//    @AjaxResponse
//    public boolean create(@RequestBody BFInitParam param) {
//        return bloomFilterDDLService.reCreate(param);
//    }
//
//
//}
