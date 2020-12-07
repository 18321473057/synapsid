package com.line.base.web.response.annotation;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.line.base.core.dto.BasicResponse;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 分页请求返回对象; PageHelper的Page对象
 */
public class PageResponseDto<T> extends BasicResponse<T> {
    //列表数据
    private T rows;
    //分页总行数
    private Long total;

    public static PageResponseDto success(Page page) {
        PageResponseDto response = getScuuess(PageResponseDto.class);
        response.rows = page;
        response.total = page.getTotal();
        return response;
    }

    public static PageResponseDto success(PageInfo pageInfo) {
        PageResponseDto response = getScuuess(PageResponseDto.class);
        response.rows = pageInfo.getList();
        response.total = pageInfo.getTotal();
        return response;
    }

    public static <T> PageResponseDto<T> error() {
        return getError(PageResponseDto.class);
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
