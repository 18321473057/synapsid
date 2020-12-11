package com.line.common.bloomfiltermanage.dao;

import com.line.base.datasource.mybatis.basedao.BaseDao;
import com.line.common.bloomfiltermanage.po.BloomFilterLogPo;

/**
 * @Author: yangcs
 * @Date: 2020/12/8 9:25
 * @Description:
 */
public interface BloomFilterLogMapper extends BaseDao<BloomFilterLogPo> {

    void update2PastDue(BloomFilterLogPo updatePO);

}
