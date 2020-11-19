package com.line.base.web.request;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoteRequestDto<?> that = (RemoteRequestDto<?>) o;
        return timestamp == that.timestamp &&
                Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, messageId);
    }

    @Override
    public String toString() {
        return "RemoteRequestDto{" +
                "timestamp=" + timestamp +
                ", messageId='" + messageId + '\'' +
                '}';
    }
}
