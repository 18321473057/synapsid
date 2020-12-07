package com.line.common.bloomfiltermanage.service.impl;

import com.line.common.bloomfiltermanage.service.IBloomFilterElementInitService;
import com.line.common.bloomfiltermanage.sql.SqlExeuter;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;

/**
 * @Author: yangcs
 * @Date: 2020/12/4 13:46
 * @Description:
 */

@Service
public class BloomFilterElementInitService implements IBloomFilterElementInitService {

    @Autowired
    private SqlExeuter sqlExeuter;

    @Override
    public long initElementByTable(RBloomFilter rf, String table, String[] fieldNames, String joinChar) {
        if(StringUtils.isEmpty(table) || fieldNames.length<1 ){
            throw new NullPointerException("名为["+rf.getName()+"]的过滤器初始化失败!table或者fieldNames为null");
        }


        return 0;
    }



    @Override
    public long initElementBySql(RBloomFilter rf, String querySql) {
        if(StringUtils.isEmpty(querySql)){
            throw new NullPointerException("名为["+rf.getName()+"]的过滤器初始化失败!querySql为null");
        }
        Set<String> elements = sqlExeuter.executeSql(querySql);
        for (String element : elements) {
            rf.add(element);
        }
        return elements.size();
    }



    @Override
    public long initElementBySupplier(RBloomFilter rf, Supplier<Set<String>> supplier) {
        return 0;
    }
}
