package com.line.base.web.dataprovider;

/**
 * @version V1.0
 * @Title: ISecureKeyProvider
 * @Package com.hoau.zodiac.core.dataprovider
 * @Description: 根据apiKey获取私钥数据
 * @date 2017/8/21 17:00
 */
public interface ISecureKeyProvider {

    /**
     * 根据apiKey获取secureKey
     *
     * @param apiKey
     * @return
     * @date 2017年08月21日17:10:54
     */
    String getSecureKey(String apiKey);

}
