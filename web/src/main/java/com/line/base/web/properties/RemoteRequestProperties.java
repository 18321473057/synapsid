package com.line.base.web.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description:
 */
@Component("remoteRequestProperties")
@ConfigurationProperties(prefix = "line.web.client.security")
public class RemoteRequestProperties {

    private Map<String,String> securityMap;


    public Map<String, String> getSecurityMap() {
        return securityMap;
    }

    public void setSecurityMap(Map<String, String> securityMap) {
        this.securityMap = securityMap;
    }
}

