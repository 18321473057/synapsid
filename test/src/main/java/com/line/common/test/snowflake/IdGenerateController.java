package com.line.common.test.snowflake;

import com.line.base.core.generate.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;


/**
 * @author yangcs
 * @version V1.0
 * @description id 生成接口服务
 */
@RestController
@RequestMapping(value = "/generator")
//@Api(value = "/generator", description = "ID 生成微服务")
public class IdGenerateController {

    @Autowired
    SnowflakeIdWorker snowflakeIdWorker;

    //    @ApiOperation(value = "获取主键", notes = "无需传入任何参数，产生全局唯一 ID，ID为类型为long，长度不定长")
    @GetMapping("/id")
    public Long idProvider() {
        return snowflakeIdWorker.nextId();
    }

    //    @ApiOperation(value = "批量获取主键id", notes = "批量产生全局唯一 ID，ID为类型为long，长度不定长")
    @GetMapping("/id/{quantity}")
    public String idProviderForBatch(@PathVariable("quantity") Integer quantity) {
        StringBuilder result = new StringBuilder();
        if (quantity == 0) {
            return String.valueOf(snowflakeIdWorker.nextId());
        } else if (quantity > 100) {
            return "Sorry , quantity too large";
        }
        for (int i = 0, len = quantity; i < len; i++) {
            try {
                Thread.sleep((long) quantity);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.append(snowflakeIdWorker.nextId()).append(",");
        }
        return result.deleteCharAt(result.length() - 1).toString();
    }
}
