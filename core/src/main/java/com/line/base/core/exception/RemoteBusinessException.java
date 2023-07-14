package com.line.base.core.exception;

import com.line.base.core.dto.RemoteErrorItemDto;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/4/14 15:43
 * @Description: 自定义远程请求异常;
 */
public class RemoteBusinessException extends RuntimeException {

    private String code;
    //请求消息ID
    private String messageId;
    //时间戳
    private long timestamp;


    private List<RemoteErrorItemDto> errorList;


    public RemoteBusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public RemoteBusinessException(List<RemoteErrorItemDto> errorList) {
        this.errorList = errorList;
    }

    public RemoteBusinessException(String code, String message, List<RemoteErrorItemDto> errorList) {
        super(message);
        this.code = code;
        this.errorList = errorList;
    }

    public RemoteBusinessException() {

    }

    public RemoteBusinessException(String message) {
        super(message);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<RemoteErrorItemDto> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<RemoteErrorItemDto> errorList) {
        this.errorList = errorList;
    }
}
