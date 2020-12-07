package com.line.common.bloomfiltermanage.manage;

import com.alibaba.fastjson.JSONObject;
import com.line.base.core.dto.AjaxResponseDto;
import com.line.base.core.exception.BusinessException;
import com.line.common.bloomfiltermanage.service.BloomFilterApiService;
import com.line.common.bloomfiltermanage.service.IBloomFilterElementInitService;
import com.line.common.cache.redis.bloom.BFInitParam;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yangcs
 * @Date: 2020/12/3 13:29
 * @Description: 布隆过滤器 ddl;对本服务提供删除新建重建 功能
 */
@Service
public class BloomFilterDDLService {

    @Autowired
    private BloomFilterApiService bloomFilterApiService;

    @Autowired
    private IBloomFilterElementInitService bloomFilterElementInitService;

    private static final Logger logger = LoggerFactory.getLogger(BloomFilterDDLService.class);

    //TODO 全程留痕呢?  邮件服务呢?


    /**
     * 删除 布隆过滤器
     */
    public int delt(String bfName) {
        if (!BloomRegisterManager.getInstance().isRegister(bfName)) {
            logger.error("删除名为[{}]的布隆过滤器失败,注册其中没有该过滤器!", bfName);
            return -1;
        }
        RBloomFilter rBloomFilter = BloomRegisterManager.getInstance().unRegister(bfName);
        rBloomFilter.delete();
        logger.info(">>>>>>>> 删除名为[{}]的布隆过滤器!", bfName);
        return 1;
    }


    /**
     * @Description 强制创建 布隆过滤器,如果过滤器已经存在 则删除 重建;
     */
    public boolean reCreate(BFInitParam param) {
        if (StringUtils.isEmpty(param.getBfName()) || param.getElementSize() == 0 || param.getFalseProbability() == 0) {
            logger.error(">>>>>>>> 初始化布隆过滤器失败,参数异常;param={}", JSONObject.toJSON(param));
            throw new BusinessException("初始化布隆过滤器失败,参数异常;");
        }

        try {
            //强制重建,不管有没有, 都先删了再说
            delt(param.getBfName());

            //构建过滤器
            AjaxResponseDto<RBloomFilter> ajaxResponseDto = bloomFilterApiService.tryInitBloomFilter(param);
            if (!ajaxResponseDto.getSuccess()) {
                throw new BusinessException("创建名为[" + param.getBfName() + "]的过滤器失败!");
            }

            //选择初始化方式,  初始化过滤器数据
            selectInitElement(ajaxResponseDto.getObj(), param);


        } catch (Exception e) {
            //删除重建 应该具有事务性,但这里没有事务; 只能邮件提醒开发者亲自去查看
            //TODO  邮件警告
            //创建失败 删除可能遗留下的数据
            logger.error(">>>>>>>> 强制创建布隆过滤器失败,e={}", e);
            delt(param.getBfName());
            return false;
        }

        return true;
    }

    private void selectInitElement(RBloomFilter bloomFilter, BFInitParam param) {
        if (param.getElementInitType() == 1) {
            //TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            //table类型
            bloomFilterElementInitService.initElementByTable(null, null, null, null);
        } else if (param.getElementInitType() == 2) {
            //sql类型
            long count = bloomFilterElementInitService.initElementBySql(bloomFilter, param.getQuerySql());
            logger.info(">>>>>>>> 初始化名为[{}]的过滤器,初始化元素数量 count=[{}]", param.getBfName(), count);
        } else {
            throw new BusinessException("不支持的初始化元素 方式!!!!");
        }
    }


}
