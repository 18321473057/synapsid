package com.line.common.cache.redis.bloom;

/**
 * @Author: yangcs
 * @Date: 2020/12/3 13:31
 * @Description: 布隆过滤器 初始化参数类
 */
public class BFInitParam {

    //一个redis服务类,对应一个过滤器
    private String redisUUID;

    //元素个数
    private long elementSize;

    //冲突率
    private double falseProbability;

    //1:table 2:sql
    private int elementInitType;

    //表名
    private String tableName;

    //sql
    private String querySql;


    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public long getElementSize() {
        return elementSize;
    }

    public void setElementSize(long elementSize) {
        this.elementSize = elementSize;
    }


    public double getFalseProbability() {
        return falseProbability;
    }

    public void setFalseProbability(double falseProbability) {
        this.falseProbability = falseProbability;
    }


    public String getRedisUUID() {
        return redisUUID;
    }

    public void setRedisUUID(String redisUUID) {
        this.redisUUID = redisUUID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getElementInitType() {
        return elementInitType;
    }

    public void setElementInitType(int elementInitType) {
        this.elementInitType = elementInitType;
    }
}
