package com.line.common.test.resposttest;

import com.github.pagehelper.PageInfo;
import com.line.base.web.request.RemoteRequestDto;
import com.line.base.web.request.annotation.RemoteResponse;
import com.line.base.web.response.annotation.AjaxResponse;
import com.line.base.web.response.annotation.PageResponse;
import com.line.common.test.mybatis.service.IMybatisService;
import com.line.common.test.mybatis.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/11/5 15:10
 * @Description:
 */

@Controller
@RequestMapping("/tesp")
public class RespTestController {

    @Autowired
    private IMybatisService mybatisService;

    @RequestMapping("/ajaxresp")
    @AjaxResponse
    public TestModel testAjaxResp() {
        TestModel t = new TestModel();
        t.setAge("12");
        t.setName("杨传顺");
        return t;
    }

    @RequestMapping("/ajaxresp1")
    @AjaxResponse
    public Object business1() {
        return "333";
    }

    @RequestMapping("/ajaxresp2")
    @AjaxResponse
    public void business2() {
    }

    @RequestMapping("/page")
    @PageResponse
    public List testpage(TestVo vo) {
        return mybatisService.listPage(vo);
    }

    @RequestMapping("/pageInfo")
    @PageResponse
    public PageInfo testpageinfo(TestVo vo) {
        return mybatisService.listpageInfo(vo);
    }

    @RequestMapping("/remote")
    @RemoteResponse
    public List<TestModel> testremote(@RequestBody RemoteRequestDto<List<TestModel>> vo, HttpSession s, TestModel r, int q, String c, char a1) {
        return new ArrayList<TestModel>();
    }


    @RequestMapping("/remote1")
    @RemoteResponse
    public TestModel testremot1e(String name, String messageId, HttpServletRequest request) {
        String messageId1 = request.getParameter("messageId");
        TestModel t = new TestModel();
        t.setAge("12");
        t.setName("杨传顺");
        return t;
    }
}