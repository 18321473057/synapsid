package com.line.base.web.login;

/**
 * @Author: yangcs
 * @Date: 2020/11/17 12:56
 * @Description: 远程加密信息类
 */
public class RemoteSecurity {
    //密文
    private String Authorization;

    //appKey 由appkey获取秘钥,用于加密秘钥不可以通过接口传递,
    private String appkey;


    // 请求的数据,未被加密的数据
    private String securityBody;

    public String getSecurityBody() {
        return securityBody;
    }

    public void setSecurityBody(String securityBody) {
        this.securityBody = securityBody;
    }

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }


    @Override
    public String toString() {
        return "RemoteSecurity{" +
                "Authorization='" + Authorization + '\'' +
                ", appkey='" + appkey + '\'' +
                ", securityBody='" + securityBody + '\'' +
                '}';
    }
}
