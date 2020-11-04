package com.line.common.test.security.addressvalid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yangcs
 * @Date: 2020/10/14 15:40
 * @Description: 用户访问地址权限校验
 */
@RestController
@RequestMapping("/address/valid")
public class AddressValidController {

    @RequestMapping("/v")
    public Boolean validAddress() {
        return true;
    }

    @RequestMapping("/c")
    public Boolean c() {
        return true;
    }


}
