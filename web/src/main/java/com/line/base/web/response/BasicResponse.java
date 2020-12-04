package com.line.base.web.response;


import com.line.base.web.exception.BusinessException;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 统一返回对象基类
 */
public class BasicResponse<T> {

    //成功与否
    protected Boolean success;
    //返回code
    protected String code;
    //返回信息
    protected String msg;

    protected static <R extends BasicResponse> R getScuuess(Class<R> cr) {
        try {
            R r = cr.newInstance();
            r.setSuccess(true);
            r.setCode("200");
            r.setMsg("操作成功!");
            return r;
        } catch (InstantiationException e) {
            throw new BusinessException("创建统一返回对象基类,cr.newInstance 发生异常");
        } catch (IllegalAccessException e) {
            throw new BusinessException("创建统一返回对象基类,cr.newInstance 发生异常");
        }
    }


    protected static <R extends BasicResponse> R getError(Class<R> cr) {
        try {
            R r = cr.newInstance();
            r.setSuccess(false);
            r.setCode("500");
            r.setMsg("系统异常!");
            return r;
        } catch (InstantiationException e) {
            throw new BusinessException("创建统一返回对象基类,cr.newInstance 发生异常");
        } catch (IllegalAccessException e) {
            throw new BusinessException("创建统一返回对象基类,cr.newInstance 发生异常");
        }
    }

    public BasicResponse() {

    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BasicResponse{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
