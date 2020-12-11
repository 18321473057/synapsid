package com.line.common.bloomfiltermanage.service;

import com.line.common.cache.redis.bloom.BFInitParam;

/**
 * @Author: yangcs
 * @Date: 2020/12/8 9:11
 * @Description: 布隆过滤器日志服务
 */
public interface IBloomFilterLogService {


    void logSuccessGet(String bfName);

    void logErrorGet(String bfName, String msg);

    void logDel(String bfName);

    void logCreate(BFInitParam param);

    void logErrorCreate(BFInitParam param, String message);
}
