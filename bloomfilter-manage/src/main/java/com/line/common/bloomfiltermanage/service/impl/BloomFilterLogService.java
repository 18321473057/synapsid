package com.line.common.bloomfiltermanage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.line.common.bloomfiltermanage.dao.BloomFilterLogMapper;
import com.line.common.bloomfiltermanage.po.BloomFilterLogPo;
import com.line.common.bloomfiltermanage.service.IBloomFilterLogService;
import com.line.common.cache.redis.bloom.BFInitParam;
import com.line.common.cache.redis.utils.BFNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: yangcs
 * @Date: 2020/12/8 9:11
 * @Description: 布隆过滤器日志服务
 */
@Service
public class BloomFilterLogService implements IBloomFilterLogService {

    @Autowired
    private BloomFilterLogMapper bloomFilterLogMapper;

    @Autowired
    private JdbcTemplate template;

    @Override
    @Transactional
    @Async
    public void logSuccessGet(String bfName) {
        BloomFilterLogPo insertPo = new BloomFilterLogPo();
        insertPo.setBfName(bfName);
        insertPo.setUseType(1);
        insertPo.setOperationType("R");
        insertPo.setConstructionBody(bfName);

        this.setBasefiled(insertPo);
        bloomFilterLogMapper.insertSelective(insertPo);
    }

    @Override
    @Transactional
    @Async
    public void logErrorGet(String bfName, String msg) {
        BloomFilterLogPo insertPo = new BloomFilterLogPo();
        insertPo.setBfName(bfName);
        insertPo.setUseType(1);
        insertPo.setOperationType("R");
        insertPo.setConstructionBody(bfName);
        insertPo.setSuccessType(0);
        insertPo.setRemark(msg);
        this.setBasefiled(insertPo);
        bloomFilterLogMapper.insertSelective(insertPo);
    }

    @Override
    @Transactional
    @Async
    public void logDel(String bfName) {
        BloomFilterLogPo insertPo = new BloomFilterLogPo();
        insertPo.setBfName(bfName);
        insertPo.setUseType(1);
        insertPo.setOperationType("D");
        insertPo.setConstructionBody(bfName);
        insertPo.setSuccessType(1);
        this.setBasefiled(insertPo);
        bloomFilterLogMapper.insertSelective(insertPo);
    }

    @Override
    @Transactional
    @Async
    public void logCreate(BFInitParam param) {
        String bfName = BFNameUtils.getBfName(param.getRedisUUID());

        //过去创建信息 设置为 0
        BloomFilterLogPo updatePO = new BloomFilterLogPo();
        updatePO.setRemark("成功重建过滤器,将本条信息设置为过期");
        updatePO.setBfName(bfName);
        this.update2PastDue(updatePO);

        BloomFilterLogPo insertPo = new BloomFilterLogPo();
        insertPo.setBfName(bfName);
        insertPo.setUseType(1);
        insertPo.setOperationType("C");
        insertPo.setConstructionBody(JSONObject.toJSONString(param));
        insertPo.setSuccessType(1);
        this.setBasefiled(insertPo);
        bloomFilterLogMapper.insertSelective(insertPo);
    }

    private void update2PastDue(BloomFilterLogPo updatePO) {
        String uptSql = "    UPDATE t_bloomfilter_log" +
                "        SET" +
                "            use_type = 0, " +
                "            modify_time = now()," +
                "            remark = '" + updatePO.getRemark() +
                "'        WHERE bf_name = '" + updatePO.getBfName() + "'  and use_type = 1";
        template.execute(uptSql);
    }

    @Override
    public void logErrorCreate(BFInitParam param, String message) {
        String bfName = BFNameUtils.getBfName(param.getRedisUUID());

        //过去创建信息 设置为 0
        BloomFilterLogPo updatePO = new BloomFilterLogPo();
        updatePO.setRemark("失败重建过滤器,该过滤器被删除");
        updatePO.setBfName(bfName);
        this.update2PastDue(updatePO);

        BloomFilterLogPo insertPo = new BloomFilterLogPo();
        insertPo.setBfName(bfName);
        insertPo.setUseType(1);
        insertPo.setOperationType("C");
        insertPo.setConstructionBody(JSONObject.toJSONString(param));
        insertPo.setSuccessType(0);
        insertPo.setRemark(message);
        this.setBasefiled(insertPo);
        bloomFilterLogMapper.insertSelective(insertPo);
    }

    private void setBasefiled(BloomFilterLogPo insertPo) {
        insertPo.setCreateTime(new Date());
        insertPo.setCreateUserCode("ycs");
        insertPo.setModifyTime(new Date());
        insertPo.setModifyUserCode("ycs");
    }
}
