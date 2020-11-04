package com.line.base.web.response;



/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: crms项目前段页面统一返回对象
 */
public class BaseResponse<T> {
    //本次请求Id
    protected   String msgId;
    //本次请求时间戳
    protected long timestamp;
    //成功与否
    protected Boolean success;
    //返回code
    protected String code;
    //返回信息
    protected String msg;


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
}
