package com.line.common.test.test;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yangcs
 * @Date: 2020/9/9 14:15
 * @Description: 集团产业链下游名单 z
 */
public class CRDownstreamDto {
    @JsonIgnore
    private String crmsId;
    @JsonIgnore
    private Date buildDate;



    //    客户名称
    private String clientName;

    private String acctcode;
    //    与天能合作年限
    private Integer cooperationYears;

    //    上年与天能供/销货金额
    private BigDecimal lastYearSalesTn;
    //    联系人
    private String linkman;

    //    联系方式
    private String mobile;
    //    联系地址
    private String address;

    public String getCrmsId() {
        return crmsId;
    }

    public void setCrmsId(String crmsId) {
        this.crmsId = crmsId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getCooperationYears() {
        return cooperationYears;
    }

    public void setCooperationYears(Integer cooperationYears) {
        this.cooperationYears = cooperationYears;
    }

    public BigDecimal getLastYearSalesTn() {
        return lastYearSalesTn;
    }

    public void setLastYearSalesTn(BigDecimal lastYearSalesTn) {
        this.lastYearSalesTn = lastYearSalesTn;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public String getAcctcode() {
        return acctcode;
    }

    public void setAcctcode(String acctcode) {
        this.acctcode = acctcode;
    }
}
