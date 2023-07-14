package com.line.base.web.login;

import com.line.base.web.properties.RemoteRequestProperties;
import com.line.common.utils.bean.MapUtils;
import com.line.common.utils.encryption.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 调用签名接口的拦截器
 */
@Component
public class SignatureRequestInterceptor implements ClientHttpRequestInterceptor, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(SignatureRequestInterceptor.class);
    private static ApplicationContext applicationContext;
    /**
     * 用于签名的公钥
     */
    private String apiKey;

    /**
     * 签名私钥
     */
    private String securityKey;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        RemoteRequestProperties properties = applicationContext.getBean(RemoteRequestProperties.class);
        //apiKey 由appkey获取秘钥,用于加密秘钥不可以通过接口传递,
        String apiKey;
        // 请求的数据,未被加密的数据
        String securityBodyStr;
        //密文
        String authorization;
        //请求方法
        String httpMethod;
        //请求uri
        String requestUri;
        //请求消息ID
        String messageId;
        //时间戳
        long timestamp;


        //调用inner的地址都需要进行签名
        String requestUrl = request.getURI().toString();
        HttpMethod method = request.getMethod();
        //获取请求参数
        String paramString = "";
        if (HttpMethod.GET.equals(method)) {
            String queryString = request.getURI().getQuery();
            logger.info("queryString:" + queryString);
            //多查询参数进行排序
            Map<String, Object> paramMap = new HashMap<>();
            if (!StringUtils.isEmpty(queryString)) {
                String[] params = queryString.split("&");
                for (String param : params) {
                    if (!StringUtils.isEmpty(param)) {
                        String[] paramPair = param.split("=");
                        if (paramPair != null && paramPair.length == 2) {
                            paramMap.put(paramPair[0], paramPair[1]);
                        }
                    }
                }
            }
            paramString = MapUtils.createLinkString(paramMap);
        } else {
            paramString = new String(body, Charset.forName("UTF-8"));
        }
        //
        String signStr = request.getURI().getPath() + "|"
                + paramString + "|"
                + getApiKey() + "|"
                + getSecurityKey();
        String signResult = MD5Utils.sign(signStr, "UTF-8");
        HttpHeaders headers = request.getHeaders();
        headers.add("ApiKey" , getApiKey());
        headers.add("Authorization" , signResult);
        headers.add("TimeStamp" , String.valueOf(System.currentTimeMillis()));
        logger.info("request the itf:" + requestUrl);
        logger.info("signStr:" + signStr);
        logger.info("signResult:" + signResult);
        ClientHttpResponse response = execution.execute(request, body);
        logger.info("request the itf success");
        return response;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SignatureRequestInterceptor.applicationContext = applicationContext;
    }
}
