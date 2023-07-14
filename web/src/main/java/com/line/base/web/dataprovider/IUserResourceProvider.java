package com.line.base.web.dataprovider;

import com.line.base.web.entity.IResource;
import com.line.base.web.entity.IUser;

import java.util.List;

/**
 * @version V1.0
 * @Title: IUserResourceProvider
 * @Package com.hoau.leo.common.dataprovider
 * @Description: 用户访问权限数据提供者
 * @date 2017/8/4 17:19
 */
public interface IUserResourceProvider {

    /**
     * 根据用户获取用户的访问权限
     *
     * @param user
     * @param systemCode
     * @return
     * @date 2017年08月04日17:23:34
     */
    List<IResource> getResources(IUser user, String systemCode);

}
