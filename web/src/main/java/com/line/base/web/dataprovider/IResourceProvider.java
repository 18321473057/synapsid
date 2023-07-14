package com.line.base.web.dataprovider;

import com.line.base.web.entity.IResource;

/**
 * @version V1.0
 * @Title: IResourceProvider
 * @Package com.hoau.leo.common.dataprovider
 * @Description: 权限数据提供接口，需要实现根据访问的uri构建权限对象
 * @date 2017/8/6 09:23
 */
public interface IResourceProvider {

    /**
     * 根据请求的uri构建resource对象
     *
     * @param accessUri
     * @param systemCode
     * @return
     * @date 2017年08月06日09:26:04
     */
    IResource getResource(String accessUri, String systemCode);

}
