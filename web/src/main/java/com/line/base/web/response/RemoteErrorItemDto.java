package com.line.base.web.response;


/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求异常行对象
 */
public class RemoteErrorItemDto {

    private String code;
    private String msg;


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
}
