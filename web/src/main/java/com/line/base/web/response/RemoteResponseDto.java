package com.line.base.web.response;


import com.line.base.web.exception.RemoteBusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * @Author: yangcs
 * @Date: 2020/3/27 14:04
 * @Description: 远程请求返回对象
 */
public class RemoteResponseDto<T> extends BasicResponse<T> {


    //请求消息ID
    private String messageId;
    //时间戳
    private long timestamp;

    //返回对象
    private T result;

    public RemoteResponseDto() {

    }

    public static <T> RemoteResponseDto<T> success(String messageId, long timestamp, T result) {
        RemoteResponseDto response = getScuuess(RemoteResponseDto.class);
        response.setMessageId(messageId);
        response.setTimestamp(timestamp);
        response.result = result;
        return response;
    }

    public static <T> RemoteResponseDto<T> error(RemoteBusinessException rException) {
        //code和msg覆盖; 没有就默认 500 /系统异常
        RemoteResponseDto response = getError(RemoteResponseDto.class);
        if(StringUtils.isNotEmpty(rException.getMessageId())){
            response.setMessageId(rException.getMessageId());
        }
        if(rException.getTimestamp() != 0){
            response.setTimestamp(rException.getTimestamp());
        }
        if (StringUtils.isNotEmpty(rException.getCode())) {
            response.setCode(rException.getCode());
        }
        if (StringUtils.isNotEmpty(rException.getMessage())) {
            response.setMsg(rException.getMessage());
        }
        if (!CollectionUtils.isEmpty(rException.getErrorList())) {
            response.result = rException.getErrorList();
        }
        return response;
    }


    public static <T> RemoteResponseDto<T> error(String messageId, long timestamp, RemoteBusinessException rException) {
        //code和msg覆盖; 没有就默认 500 /系统异常
        RemoteResponseDto response = getError(RemoteResponseDto.class);
        if (StringUtils.isNotEmpty(rException.getCode())) {
            response.setCode(rException.getCode());
        }
        if (StringUtils.isNotEmpty(rException.getMessage())) {
            response.setMsg(rException.getMessage());
        }
        if (!CollectionUtils.isEmpty(rException.getErrorList())) {
            response.result = rException.getErrorList();
        }
        response.setMessageId(messageId);
        response.setTimestamp(timestamp);
        return response;
    }

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
