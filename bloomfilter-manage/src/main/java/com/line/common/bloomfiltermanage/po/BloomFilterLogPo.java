package com.line.common.bloomfiltermanage.po;

import com.line.base.datasource.mybatis.basePo.BasicEntity;

import javax.persistence.Table;

/**
 * @Author: yangcs
 * @Date: 2020/12/8 9:04
 * @Description:
 */
@Table(name="t_bloomfilter_log")
public class BloomFilterLogPo extends BasicEntity {
    // 布隆过滤器名称
    private String bfName;
    // 0:过期被覆盖, 1:最新使用
    private Integer useType;
    //操作类型, C R D 增加(Create)、检索(Retrieve)、删除(Delete)
    private String operationType;
    //构造体信息
    private String constructionBody;
    // '1: 成功  0:失败';
    private Integer successType;
    //'备注'
    private String remark;


    public String getBfName() {
        return bfName;
    }

    public void setBfName(String bfName) {
        this.bfName = bfName;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getConstructionBody() {
        return constructionBody;
    }

    public void setConstructionBody(String constructionBody) {
        this.constructionBody = constructionBody;
    }

    public Integer getSuccessType() {
        return successType;
    }

    public void setSuccessType(Integer successType) {
        this.successType = successType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
