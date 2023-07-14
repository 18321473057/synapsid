package com.line.common.test.cache;

import com.line.common.cache.interf.ITTLCacheProvider;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserCacheProvider implements ITTLCacheProvider<UserDTO> {

    Map<String, UserDTO> userDTOMap = new LinkedHashMap<String, UserDTO>() {{
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setAlias("ycs");
        userDTO1.setName("杨传顺");
        userDTO1.setAge("28");
        userDTO1.setRemark("我是一个傻逼");
        put(userDTO1.getAlias(), userDTO1);
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setAlias("wyz");
        userDTO2.setName("魏燕子");
        userDTO2.setAge("29");
        userDTO2.setRemark("他是一个傻逼");
        put(userDTO2.getAlias(), userDTO2);
    }};

    public UserDTO get(String s) {
        return userDTOMap.get(s);
    }


}
