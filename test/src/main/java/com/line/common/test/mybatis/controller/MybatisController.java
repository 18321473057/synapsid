package com.line.common.test.mybatis.controller;

import com.line.common.test.mybatis.po.TestPo;
import com.line.common.test.mybatis.service.IMybatisService;
import com.line.common.test.mybatis.service.ISwitchoverService;
import com.line.common.test.mybatis.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/10/23 13:39
 * @Description:
 */
@RestController
@RequestMapping("/mybatis")
public class MybatisController {

    @Autowired
    private IMybatisService mybatisService;
    @Autowired
    private ISwitchoverService switchoverService;

    /**
     * 通用mapper
     */
    @RequestMapping("/tk/sltone")
    public TestPo selectOneByTKMapper(@RequestParam String id) {
        return mybatisService.selectOneByTKMapper(id);
    }

    /**
     * 通用mapper
     */
    @RequestMapping("/tk/instone")
    public int instOneByTKMapper(@RequestParam String id) {
        return mybatisService.instOneByTKMapper(id);
    }

    /**分页助手*/

    @RequestMapping("/list/page")
    public List<TestVo> listPage(TestVo vo) {
        return mybatisService.listPage(vo);
    }

    @RequestMapping("/test")
    public String test(@RequestParam String type) {
        if ("slave".equals(type)) {
            return mybatisService.selectAnySlave();
        } else if ("all".equals(type)) {
            return switchoverService.selectAnyAll();
        } else {
            return mybatisService.selectAnyMaster();
        }
    }

}
