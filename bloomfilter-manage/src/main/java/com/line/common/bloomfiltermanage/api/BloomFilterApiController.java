package com.line.common.bloomfiltermanage.api;

import com.line.base.core.dto.AjaxResponseDto;
import com.line.common.bloomfiltermanage.service.BloomFilterApiService;
import org.redisson.RedissonBloomFilter;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: yangcs
 * @Date: 2020/12/3 17:26
 * @Description: 布隆过滤器服务 接口
 * <p>
 * 注意:::: 各服务本地应也保存一个副本,如果在本地查询不到再请求本服务获取;
 */
@Controller
@RequestMapping("/api/bf")
public class BloomFilterApiController {

    @Autowired
    private BloomFilterApiService bloomFilterIniter;

    //  想了想 布隆过滤器DDL所有的权限 都集中在本服务好了;

//    /**
//     * 尝试初始化,但不会覆盖原有有效的过滤器;
//     * 1: 第一次初始化 可以由各个服务来进行;
//     * 2: 重建必须有本服务进行
//     * warning:::  注意本服务 由于被多服务初始化时就调用;  tryInitRBloomFilter 肯定会被多次调用; 所以重复尝试初始化的时候 不能报错(不能校验重复的过滤器名称),要正常返回过滤器
//     * 只能在各个服务中 再注册一遍,校验重复名称
//     */
//    @RequestMapping("/try/init")
//    @ResponseBody
//    public AjaxResponseDto tryInitRBloomFilter(BFInitParam param) {
//        return bloomFilterIniter.tryInitBloomFilter(param);
//    }

    /**
     * 尝试获取过滤器
     *
     */
    @GetMapping("/try/get")
    @ResponseBody
    public AjaxResponseDto<RedissonBloomFilter> tryGetRBloomFilter(String bfName) {
        return bloomFilterIniter.tryGetRBloomFilter(bfName);
    }

}
