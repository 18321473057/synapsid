package com.line.common.test.test;

import com.alibaba.fastjson.JSONObject;
import com.line.base.web.login.SignatureRequestInterceptor;
import com.line.common.utils.bean.MapUtils;
import com.line.common.utils.encryption.MD5Utils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Author: yangcs
 * @Date: 2020/11/18 14:26
 * @Description:
 */
@Controller
@RequestMapping("/test/remote")
public class RemoteReqTest {

    @RequestMapping("/1")
    @ResponseBody
    public Response<CRDownstreamDto> test() {
        //时间戳
        long timestamp = System.currentTimeMillis();
        //消息ID(不可重复)
        String messageId = "30594826051";
        //指定的 apiKey
        String apiKey = "CRRAuthorization";
        //查询参数 acctCode
        String acctCode = "1001446";

        //根据参数key排序;
        Map<String, Object> map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("messageId", messageId);
        map.put("acctCode", acctCode);
        String paramString = MapUtils.createLinkString(map);

        //指定的秘钥
        String securityKey = "cr123";
        //请求uri
        String uri = "/action/remote/api/cr/dealer/info/get";
        //组装要加密的字符串
        String signStr = uri + "|" + paramString + "|" + apiKey + "|" + securityKey;

        //拼接get url
        String fullUrl = "http://192.168.51.242/" + uri +"?"+paramString;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        headers.set("Accept", "application/json");
        headers.set("apiKey", apiKey);
        headers.set("token", MD5Utils.sign(signStr, "UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("未被加密的信息" + signStr);
        System.out.println("加密信息" + MD5Utils.sign(signStr, "UTF-8"));
        String json = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class).getBody();
        return JSONObject.parseObject(json, Response.class);
    }



    @RequestMapping("/2")
    @ResponseBody
    public Response<CRDownstreamDto> test2() {
        //时间戳
        long timestamp = System.currentTimeMillis();
        //消息ID(不可重复)
        String messageId = "30594826051";
        //指定的 apiKey
        String apiKey = "CRRAuthorization";
        //查询参数 acctCode
        String acctCode = "1001446";

        //根据参数key排序;
        Map<String, Object> map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("messageId", messageId);
        map.put("acctCode", acctCode);
        String paramString = MapUtils.createLinkString(map);

        //指定的秘钥
        String securityKey = "cr123";
        //请求uri
        String uri = "/action/remote/api/cr/dealer/info/get";
        //组装要加密的字符串
        String signStr = uri + "|" + paramString + "|" + apiKey + "|" + securityKey;

        //拼接get url
        String fullUrl = "http://192.168.51.242/" + uri +"?"+paramString;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
        headers.set("Accept", "application/json");
        headers.set("apiKey", apiKey);
        headers.set("token", MD5Utils.sign(signStr, "UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();

        SignatureRequestInterceptor interceptor = new SignatureRequestInterceptor();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList();
        interceptor.setApiKey("lineApiKey");
        interceptors.add(interceptor);
        restTemplate.setInterceptors(interceptors);

        String json = restTemplate.exchange(fullUrl, HttpMethod.GET,entity, String.class).getBody();
        return JSONObject.parseObject(json, Response.class);
    }
}
