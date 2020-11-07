package com.line.base.web.response;


import com.github.pagehelper.Page;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求返回对象
 */
public class RemoteResponseDto<T> extends BasicResponse<T> {

    //时间戳
    private long lastTimestamp;

    //请求消息ID
    private String messageId;

    //异常信息列表
    //private List<RemoteErrorItemDto> errorItems;

    //返回对象
    private T result;


    public static <T> RemoteResponseDto<T> success(T result) {
        RemoteResponseDto response = getScuuess(RemoteResponseDto.class);
        response.result = result;
        return response;
    }


    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
