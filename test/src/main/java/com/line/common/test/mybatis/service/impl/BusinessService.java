package com.line.common.test.mybatis.service.impl;

import com.line.base.core.dto.RemoteErrorItemDto;
import com.line.base.core.exception.BusinessException;
import com.line.base.core.exception.RemoteBusinessException;
import com.line.common.test.mybatis.service.IBusinessService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/10/29 8:48
 * @Description:
 */
@Service
public class BusinessService implements IBusinessService {

    public String throwBusiness() {
        throw new BusinessException("333444" , "测试 exceptionHandler注解拦截效果");
    }

    public String throwBr() {
        List<RemoteErrorItemDto> errorList = new ArrayList<RemoteErrorItemDto>();
        RemoteErrorItemDto itemDto = new RemoteErrorItemDto();
        itemDto.setCode("111");
        itemDto.setMsg("AAAA");
        errorList.add(itemDto);
        RemoteErrorItemDto itemDto1 = new RemoteErrorItemDto();
        itemDto1.setCode("222");
        itemDto1.setMsg("BBB");
        errorList.add(itemDto1);
        throw new RemoteBusinessException("3334" , "ddd" , errorList);
    }
}
