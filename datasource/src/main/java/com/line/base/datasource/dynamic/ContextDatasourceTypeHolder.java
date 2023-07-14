package com.line.base.datasource.dynamic;

/**
 * @Title: ContextDatasourceTypeHolder
 * @Package com.hoau.zodiac.datasource.dynamic
 * @Description: 当前处理的数据源类型Holder
 */
public class ContextDatasourceTypeHolder {

    private static ThreadLocal<String> datasourceTypeHolder = new ThreadLocal<String>();

    /**
     * 获取当前操作的数据源类型
     */
    public static String getDatasourceType() {
        return datasourceTypeHolder.get();
    }

    /**
     * 设置当前操作的数据源类型
     */
    public static void setDatasourceType(String dataSourceType) {
        datasourceTypeHolder.set(dataSourceType);
    }

    /**
     * 清除当前操作的数据源类型，使用默认的了
     */
    public static void clearDatasourceType() {
        datasourceTypeHolder.remove();
    }

}
