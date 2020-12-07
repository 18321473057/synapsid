package com.line.common.bloomfiltermanage.service;

import com.alibaba.fastjson.JSONObject;
import com.line.base.core.exception.BusinessException;
import com.line.base.core.dto.AjaxResponseDto;
import com.line.common.bloomfiltermanage.manage.BloomRegisterManager;
import com.line.common.cache.redis.bloom.BFInitParam;
import org.apache.commons.lang3.StringUtils;
import org.redisson.RedissonBloomFilter;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangcs
 * @Date: 2020/12/3 13:29
 * @Description: 对远程服务提供查询布隆过滤器的服务;
 */
@Service
public class BloomFilterApiService {

    @Autowired
    private RedissonClient redissonClient;


    private static final Logger logger = LoggerFactory.getLogger(BloomFilterApiService.class);


    /**
     * 尝试以最大的包容去 初始化布隆过滤器, 当已经存在有效的过滤器时, 不会覆盖当前过滤器配置(直接覆盖配置会导致数据错乱,必须删除重建过滤器)也不会报错;
     * 1: redis中不存在,注册器中也不存在 , redisson 创建过滤去后,再注册到本地注册器
     * 2: redis中不存在,注册器中存在(这个几乎不存在,), 删除注册器中数据,  redisson 创建过滤去后,再注册到本地注册器
     * 3: redis中存在这个 有效的(存在元素)过滤器,且注册器中也存在; 直接返回这个过滤器;
     * 4: redis中存在 , 注册器中不存在; 将去注册到注册器
     * <p>
     * <b/> redisson的布隆过滤器 生命周期 如下:
     * 完整的布隆过滤器 例如 name ="c1" 会创建两个key ,"{c1}:config" 和 "c1";
     * 创建的时刻如下:
     * RBloomFilter<Integer> c1 = redissonClient.getBloomFilter("c1"); //什么都不会创建
     * System.out.println(c1.isExists()); //false
     * c1.tryInit(10L, 0.1);   //创建 "{c1}:config"
     * System.out.println(c1.isExists()); //false
     * c1.add(1);  //创建 "c1"
     * System.out.println(c1.isExists()); //true
     * c1.delete();  //删除  "{c1}:config" 和 "c1"
     */


    //TODO  过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,过滤器注册要留痕,
    public synchronized AjaxResponseDto<RBloomFilter> tryInitBloomFilter(BFInitParam param) {
        if (StringUtils.isEmpty(param.getBfName()) || param.getElementSize() == 0 || param.getFalseProbability() == 0) {
            logger.error(">>>>>>>> 初始化布隆过滤器失败,参数异常;param={}", JSONObject.toJSON(param));
            throw new BusinessException("初始化布隆过滤器失败,参数异常!");
        }

        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(param.getBfName());
        //redis已经存在有效过滤器,且add过元素
        if (bloomFilter.isExists()) {
            //注册器中也存在,这尼玛就是来捣乱的,重复注册; 直接返回过滤器给他, 不再鸟他
            if (BloomRegisterManager.getInstance().isRegister(bloomFilter.getName())) {
                logger.info(">>>>>>>> 初始化布隆过滤器bfName=[{}],已经存在有效过滤器.", param.getBfName());
            } else {
                //注册器中不存在, 注册下;
                BloomRegisterManager.getInstance().register(bloomFilter);
                //为什么redis有 但注册器中没有注册成功? 这里是异常行径! 打印这条日志就要 引起注意了;
                //TODO  这里可以发送一份邮件
                logger.error(">>>>>>>> 初始化布隆过滤器bfName=[{}], redis中存在,但是注册器中不存在.", param.getBfName());
            }
        } else {
            bloomFilter.delete();//(可以多次删除), 既然没有被真正的创建也没有add任何元素,那就删除可能存在的 config,使用全新的配置;
            if (BloomRegisterManager.getInstance().isRegister(bloomFilter.getName())) {
                //为什么Redis不存在但注册器中存在?这里是异常行径! 打印这条日志就要 引起注意了;
                //TODO  这里可以发送一份邮件
                //删除注册器中数据
                BloomRegisterManager.getInstance().unRegister(bloomFilter.getName());
            }


            bloomFilter = redissonClient.getBloomFilter(param.getBfName());
            bloomFilter.tryInit(param.getElementSize(), param.getFalseProbability()); //创建{bfName}:config

            //TODO  此处可以添加  初始化布隆过滤器的逻辑
            //TODO
            //TODO
            //TODO
            //TODO
            //TODO
            //TODO
            //TODO
            //TODO
            //TODO

            //注册过滤器
            BloomRegisterManager.getInstance().register(bloomFilter);
            logger.info(">>>>>>>> 初始化布隆过滤器bfName=[{}],一路顺风,创建成功.", param.getBfName());
        }
        return AjaxResponseDto.success(bloomFilter);
    }
//
//    /**
//     * 尝试 获取过滤器
//     * <p>
//     * 优先从本地注册器中获取. 如果没有从redis中获取; 再没有就返回失败
//     */
//    public AjaxResponseDto<RBloomFilter> tryGetRBloomFilter(String bfName) {
//        RBloomFilter bloomFilter = null;
//        if (!BloomRegisterManager.getInstance().isRegister(bfName)) {
//            bloomFilter = redissonClient.getBloomFilter(bfName);
//            if (bloomFilter == null || !bloomFilter.isExists()) {
//                throw new BusinessException("没有查询到名为[" + bfName + "]的过滤器");
//            }
//            //本地中没了 再注册进去;
//            BloomRegisterManager.getInstance().register(bloomFilter);
//        } else {
//            bloomFilter = BloomRegisterManager.getInstance().getBloomFilter(bfName);
//        }
//        return AjaxResponseDto.success(bloomFilter);
//    }


    /**
     * 尝试 获取过滤器
     * <p>
     * 优先从本地注册器中获取. 如果没有从redis中获取; 再没有就返回失败
     */
    public AjaxResponseDto<RedissonBloomFilter> tryGetRBloomFilter(String bfName) {
        RedissonBloomFilter bloomFilter = (RedissonBloomFilter) redissonClient.getBloomFilter(bfName);
        return AjaxResponseDto.success(bloomFilter);
    }


}
