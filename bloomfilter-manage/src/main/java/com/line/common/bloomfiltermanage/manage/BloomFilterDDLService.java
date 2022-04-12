//package com.line.common.bloomfiltermanage.manage;
//
//import com.alibaba.fastjson.JSONObject;
//import com.line.base.core.dto.AjaxResponseDto;
//import com.line.base.core.exception.BusinessException;
//import com.line.common.bloomfiltermanage.service.IBloomFilterElementInitService;
//import com.line.common.bloomfiltermanage.service.IBloomFilterLogService;
////import com.line.common.bloomfiltermanage.service.impl.BloomFilterApiService;
//import com.line.common.cache.redis.bloom.BFInitParam;
//import com.line.common.cache.redis.utils.BFNameUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.api.RBloomFilter;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @Author: yangcs
// * @Date: 2020/12/3 13:29
// * @Description: 布隆过滤器 ddl;对本服务提供删除新建重建 功能
// */
//@Service
//public class BloomFilterDDLService {
//
//    @Autowired
//    private BloomFilterApiService bloomFilterApiService;
//    @Autowired
//    private IBloomFilterElementInitService bloomFilterElementInitService;
//    @Autowired
//    private IBloomFilterLogService bloomFilterLogService;
//    @Autowired
//    private RedissonClient redissonClient;
//
//
//    private static final Logger logger = LoggerFactory.getLogger(BloomFilterDDLService.class);
//
//    //TODO 全程留痕呢?  邮件服务呢?
//
//
//    /**
//     * 删除 布隆过滤器
//     */
//    public int delt(String bfName) {
//        if (!BloomRegisterManager.getInstance().isRegister(bfName)) {
//            logger.error("删除名为[{}]的布隆过滤器失败,注册其中没有该过滤器!", bfName);
//            throw new BusinessException("删除失败");
//        }
//        RBloomFilter rBloomFilter = BloomRegisterManager.getInstance().unRegister(bfName);
//        rBloomFilter.delete();
//        bloomFilterLogService.logDel(bfName);
//        logger.info(">>>>>>>> 删除名为[{}]的布隆过滤器!", bfName);
//        return 1;
//    }
//
//
//    /**
//     * @Description 强制创建 布隆过滤器,如果过滤器已经存在 则删除 重建;
//     */
//    public boolean reCreate(BFInitParam param) {
//        if (StringUtils.isEmpty(param.getRedisUUID()) || param.getElementSize() == 0 || param.getFalseProbability() == 0) {
//            logger.error(">>>>>>>> 初始化布隆过滤器失败,参数异常;param={}", JSONObject.toJSON(param));
//            throw new BusinessException("初始化布隆过滤器失败,参数异常;");
//        }
//        String bfName = BFNameUtils.getBfName(param.getRedisUUID());
//
//        try {
//            //构建过滤器
//            AjaxResponseDto<RBloomFilter> ajaxResponseDto = tryInitBloomFilter(param);
//            if (!ajaxResponseDto.getSuccess()) {
//                throw new BusinessException("创建名为[" + bfName + "]的过滤器失败!");
//            }
//            //选择初始化方式,  初始化过滤器数据
//            selectInitElement(ajaxResponseDto.getObj(), param);
//            bloomFilterLogService.logCreate(param);
//        } catch (Exception e) {
//            //删除重建 应该具有事务性,但这里没有事务; 只能邮件提醒开发者亲自去查看
//            //TODO  邮件警告
//            try {
//                delt(bfName);
//            } catch (Exception e1) {
//            }
//            //创建失败 删除可能遗留下的数据
//            logger.error(">>>>>>>> 强制创建布隆过滤器失败,请注意可能无过滤器可用! e={}", e);
//            bloomFilterLogService.logErrorCreate(param,e.getMessage());
//            return false;
//        }
//        return true;
//    }
//
//
//    /**
//     * 创建布隆过滤器
//     *
//     * @warning <b/> redisson的布隆过滤器 生命周期 如下:
//     * 完整的布隆过滤器 例如 name ="c1" 会创建两个key ,"{c1}:config" 和 "c1";
//     * 创建的时刻如下:
//     * RBloomFilter<Integer> c1 = redissonClient.getBloomFilter("c1"); //什么都不会创建
//     * System.out.println(c1.isExists()); //false
//     * c1.tryInit(10L, 0.1);   //创建 "{c1}:config"
//     * System.out.println(c1.isExists()); //false
//     * c1.add(1);  //创建 "c1"
//     * System.out.println(c1.isExists()); //true
//     * c1.delete();  //删除  "{c1}:config" 和 "c1"
//     */
//
//
//    //TODO  过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,
//    public synchronized AjaxResponseDto<RBloomFilter> tryInitBloomFilter(BFInitParam param) {
//        if (StringUtils.isEmpty(param.getRedisUUID()) || param.getElementSize() == 0 || param.getFalseProbability() == 0) {
//            logger.error(">>>>>>>> 初始化布隆过滤器失败,参数异常;param={}", JSONObject.toJSON(param));
//            throw new BusinessException("初始化布隆过滤器失败,参数异常!");
//        }
//        String bfName = BFNameUtils.getBfName(param.getRedisUUID());
//        //强制重建,不管有没有, 都先删了再说
//        try {
//            delt(bfName);
//        } catch (Exception e) {
//        }
//
//        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bfName);
//        bloomFilter.tryInit(param.getElementSize(), param.getFalseProbability()); //创建{bfName}:config
//        //注册过滤器
//        BloomRegisterManager.getInstance().register(bloomFilter);
//        logger.info(">>>>>>>> 初始化布隆过滤器bfName=[{}],一路顺风,创建成功.", bfName);
//        return AjaxResponseDto.success(bloomFilter);
//    }
//
//
//    private void selectInitElement(RBloomFilter bloomFilter, BFInitParam param) {
//        if (param.getElementInitType() == 1) {
//            //TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//            //table类型
//            bloomFilterElementInitService.initElementByTable(null, null, null, null);
//        } else if (param.getElementInitType() == 2) {
//            //sql类型
//            long count = bloomFilterElementInitService.initElementBySql(bloomFilter, param.getQuerySql());
//            logger.info(">>>>>>>> 初始化名为[{}]的过滤器,初始化元素数量 count=[{}]", BFNameUtils.getBfName(param.getRedisUUID()), count);
//        } else {
//            throw new BusinessException("不支持的初始化元素 方式!!!!");
//        }
//    }
//
//
//}
