package com.line.base.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 */
@Component("securityProperties")
@ConfigurationProperties(prefix = "line.web.security")
public class RemoteSecurityProperties {

    private Map<String,String> securityMap;


    public Map<String, String> getSecurityMap() {
        return securityMap;
    }

    public void setSecurityMap(Map<String, String> securityMap) {
        this.securityMap = securityMap;
    }
}

