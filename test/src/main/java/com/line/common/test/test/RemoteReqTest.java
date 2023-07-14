package com.line.common.test.test;

import com.alibaba.fastjson.JSONObject;
import com.line.common.utils.bean.MapUtils;
import com.line.common.utils.encryption.MD5Utils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yangcs
 * @Date: 2020/11/18 14:26
 * @Description:
 */
@Controller
@RequestMapping("/test/remote")
public class RemoteReqTest {

    //    private static String distributorUrl = "http://www.etianneng.cn/";
    private static String distributorUrl = "http://localhost/";
    //    private static String distributorUrl = "http://192.168.51.242/";
//    private static String distributorUrl = "http://192.168.34.110/";
    private static String uri = "/action/remote/api/cr/dealer/info/get";

    public static void main(String[] args) throws Exception {
        //时间戳
        long timestamp = System.currentTimeMillis();
        //消息ID(不可重复)
        String messageId = "jfbgvlasjk";
        //指定的 apiKey
        String apiKey = "CRRAuthorization";


//        1300034,1502791,1502842,1502848,1503241,1503285,1503372,1504252,1504672,1504904,
//1505002,1505243,1505450,1505489,1505556,1505736,1505836,1505987,1506122,1506237,
//1506264,1506486,1506488,1506634,1507173,1507270,1507618,1507633,1507793,1508159,
//1508165,1508188,1508296,1508505,1508539,1508745,1508776,1509093,1509304,1509680,
//1510568,1511038,1511101,1511113,1511137,1511238,1511271,1511359,1511411,1511412,
//1511413,1511425,1511439,1511444,1511445,1511510,1511531,1511532,1511542,1511558,
//1511559,1511668,1511737,1511792,1511830,1511847,1511857,1511941,1511995,1511997,
//1511998,1512149,1512203,1512288,1512310,1512311,1512407,1512495,1512834,1512885,
//1512924,1512968,1513055,1513103,1513233,1513290,1513349,1513447,1513543,1513581,@CV
        //查询参数 acctCode
        String acctCode = "1512968";
        //根据参数key排序;
        Map<String, Object> map = new HashMap();
        map.put("timestamp" , timestamp);
        map.put("messageId" , messageId);
        map.put("acctCode" , acctCode);
        String paramString = MapUtils.createLinkString(map);
        //指定的秘钥
        String securityKey = "&A^3*c#V/>$7{]0";
//        String securityKey = "cr123";
        //请求uri
        //组装要加密的字符串
        String signStr = uri + "|" + paramString + "|" + apiKey + "|" + securityKey;
        //拼接get url
        String fullUrl = distributorUrl + uri + "?" + paramString;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type" , "application/json;charset=UTF-8");
        headers.set("Accept" , "application/json");
        headers.set("apiKey" , apiKey);
        headers.set("token" , MD5Utils.sign(signStr, "UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("未被加密的信息" + signStr);
        System.out.println("加密信息" + MD5Utils.sign(signStr, "UTF-8"));
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
            String body = exchange.getBody();
            System.out.println("返回信息" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
        map.put("timestamp" , timestamp);
        map.put("messageId" , messageId);
        map.put("acctCode" , acctCode);
        String paramString = MapUtils.createLinkString(map);

        //指定的秘钥
//        String securityKey = "cr123";
        String securityKey = "&A^3*c#V/>$7{]0";
        //请求uri
        String uri = "/action/remote/api/cr/dealer/info/get";
        //组装要加密的字符串
        String signStr = uri + "|" + paramString + "|" + apiKey + "|" + securityKey;

        //拼接get url
        String fullUrl = "http://localhost/" + uri + "?" + paramString;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type" , "application/json;charset=UTF-8");
        headers.set("Accept" , "application/json");
        headers.set("apiKey" , apiKey);
        headers.set("token" , MD5Utils.sign(signStr, "UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("未被加密的信息" + signStr);
        System.out.println("加密信息" + MD5Utils.sign(signStr, "UTF-8"));
        String json = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class).getBody();
        return JSONObject.parseObject(json, Response.class);
    }


}
