package com.line.base.web.response;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 分页请求返回对象; PageHelper的Page对象
 */
public class PageResponseVo<T> extends BasicResponse<T> {
    //列表数据
    private T rows;
    //分页总行数
    private Long total;

    public static   PageResponseVo  success(Page page) {
        PageResponseVo response = getScuuess(PageResponseVo.class);
        response.rows = page;
        response.total = page.getTotal();
        return response;
    }

    public static   PageResponseVo  success(PageInfo pageInfo) {
        PageResponseVo response = getScuuess(PageResponseVo.class);
        response.rows = pageInfo.getList();
        response.total = pageInfo.getTotal();
        return response;
    }

    public static <T> PageResponseVo<T> error() {
        return getError(PageResponseVo.class);
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
