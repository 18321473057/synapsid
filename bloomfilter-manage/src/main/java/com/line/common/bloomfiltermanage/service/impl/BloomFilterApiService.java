//package com.line.common.bloomfiltermanage.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.line.base.core.dto.AjaxResponseDto;
//import com.line.base.core.exception.BusinessException;
//import com.line.common.bloomfiltermanage.manage.BloomFilterDDLService;
//import com.line.common.bloomfiltermanage.manage.BloomRegisterManager;
//import com.line.common.bloomfiltermanage.service.IBloomFilterApiService;
//import com.line.common.bloomfiltermanage.service.IBloomFilterLogService;
//import com.line.common.cache.redis.bloom.BFInitParam;
//import com.line.common.cache.redis.utils.BFNameUtils;
//import org.apache.commons.lang3.StringUtils;
////import org.redisson.api.RBloomFilter;
////import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @Author: yangcs
// * @Date: 2020/12/3 13:29
// * @Description: 对远程服务提供查询布隆过滤器的服务;
// */
//@Service
//public class BloomFilterApiService implements IBloomFilterApiService {
//
//    @Autowired
//    private RedissonClient redissonClient;
//    @Autowired
//    private IBloomFilterLogService bloomFilterLogService;
//
//    private static final Logger logger = LoggerFactory.getLogger(BloomFilterApiService.class);
//
//
//    /**
//     * 尝试 获取过滤器
//     * <p>
//     * 优先从本地注册器中获取. 如果没有从redis中获取; 再没有就返回失败
//     */
//    public AjaxResponseDto<String> tryGetRBloomFilter(String redisUUID) {
//        if (StringUtils.isEmpty(redisUUID)) {
//            throw new BusinessException("redisUUID is null!");
//        }
//        String bfName = BFNameUtils.getBfName(redisUUID);
//        RBloomFilter bloomFilter;
//        if (!BloomRegisterManager.getInstance().isRegister(bfName)) {
//            bloomFilterLogService.logErrorGet(bfName, "没有查询到名为[" + bfName + "]的过滤器");
//            throw new BusinessException("没有查询到名为[" + bfName + "]的过滤器");
//        } else {
//            bloomFilter = BloomRegisterManager.getInstance().getBloomFilter(bfName);
//        }
//        bloomFilterLogService.logSuccessGet(bfName);
//        return AjaxResponseDto.success(bloomFilter.getName());
//    }
//
//
//}
