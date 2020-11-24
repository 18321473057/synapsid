package com.line.common.test.test;


import java.io.Serializable;
import java.util.concurrent.locks.Lock;

//返回报文父类
public class ResponseBaseDto implements Serializable {
    //请求ID
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
