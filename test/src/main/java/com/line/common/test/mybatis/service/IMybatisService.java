package com.line.common.test.mybatis.service;

import com.github.pagehelper.PageInfo;
import com.line.common.test.mybatis.po.TestPo;
import com.line.common.test.mybatis.vo.TestVo;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/10/26 14:53
 * @Description:
 */
public interface IMybatisService {

    //从库中取任一
    String selectAnySlave();

    //主库中取任一
    String selectAnyMaster();

    //主从一起查
    String selectAnyAll();

    //tkmapper 查询
    TestPo selectOneByTKMapper(String id);

    //tkmapper 新增
    int instOneByTKMapper(String id);

    //分页查询
    List<TestVo> listPage(TestVo vo);

    PageInfo listpageInfo(TestVo vo);
}
