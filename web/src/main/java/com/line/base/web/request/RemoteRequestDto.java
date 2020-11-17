package com.line.base.web.request;


/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求返回对象
 */
public class RemoteRequestDto<T> {

    //时间戳
    private long timestamp;

    //请求消息ID
    private String messageId;

    //返回对象
    private T data;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RemoteRequestDto{" +
                "timestamp=" + timestamp +
                ", messageId='" + messageId + '\'' +
                ", data=" + data +
                '}';
    }
}
