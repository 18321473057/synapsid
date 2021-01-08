package com.line.common.test.push;

import com.line.base.web.response.annotation.PageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

/**
 * @Author: yangcs
 * @Date: 2021/1/7 17:18
 * @Description:
 */
@RestController
@RequestMapping("/see/push")
public class SeePushController {

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView();
    }

    @GetMapping(value = "/random",produces = "text/event-stream")
    public  Integer redorm(){
        return new Random().nextInt();
    }
}
