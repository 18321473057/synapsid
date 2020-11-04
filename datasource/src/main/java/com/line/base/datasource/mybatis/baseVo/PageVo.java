package com.line.base.datasource.mybatis.baseVo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Author: yangcs
 * @Date: 2020/10/27 17:33
 * @Description:
 */
public class PageVo {
    //当前页
    @JsonIgnore
    private int page = 1;
    //没页数
    @JsonIgnore
    private int rows = 20;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
