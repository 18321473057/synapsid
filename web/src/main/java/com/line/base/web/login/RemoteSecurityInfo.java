package com.line.base.web.login;

/**
 * @Author: yangcs
 * @Date: 2020/11/17 12:56
 * @Description: 远程加密信息类
 */
public class RemoteSecurityInfo {
    //apiKey 由appkey获取秘钥,用于加密秘钥不可以通过接口传递,
    private String apiKey;
    // 请求的数据,未被加密的数据
    private String securityBodyStr;
    //密文
    private String authorization;
    //请求方法
    private String httpMethod;
    //请求uri
    private String requestUri;
    //请求消息ID
    private String messageId;
    //时间戳
    private long timestamp;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecurityBodyStr() {
        return securityBodyStr;
    }

    public void setSecurityBodyStr(String securityBodyStr) {
        this.securityBodyStr = securityBodyStr;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
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
}
