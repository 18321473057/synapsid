package com.line.common.cache.redis.bloom.remote;

import com.line.base.core.dto.AjaxResponseDto;
import org.redisson.api.RBloomFilter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangcs
 * @Date: 2020/12/5 10:19
 * @Description:
 */
//先直连吧
@FeignClient(url = "http://127.0.0.1:9000", value = "xac")
@Service
public interface BloomFRemote {

    /**
     * 获取布隆过滤器接接口
     */
    @RequestMapping(value = "/api/bf/try/get", method = RequestMethod.GET)
    AjaxResponseDto<RBloomFilter> getBloomFilter(@RequestParam("bfName") String bfName);


}
