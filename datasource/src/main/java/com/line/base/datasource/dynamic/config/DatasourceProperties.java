package com.line.base.datasource.dynamic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description: 数据源配置
 */
@ConfigurationProperties(prefix = "line.dynamic.datasource")
public class DatasourceProperties {

    /**
     * 是否启用多数据源
     */
    private boolean enable;

    /**
     * 多数据源列表
     */
    private List<DruidProperties> datasourceList;

    /**
     * 默认数据源名称
     */
    private String defaultDatasourceName;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<DruidProperties> getDatasourceList() {
        return datasourceList;
    }

    public void setDatasourceList(List<DruidProperties> datasourceList) {
        this.datasourceList = datasourceList;
    }

    public String getDefaultDatasourceName() {
        return defaultDatasourceName;
    }

    public void setDefaultDatasourceName(String defaultDatasourceName) {
        this.defaultDatasourceName = defaultDatasourceName;
    }
}
