package com.line.common.test.mybatis.dao;

import com.line.base.datasource.mybatis.basedao.BaseDao;
import com.line.common.test.mybatis.po.TestPo;
import com.line.common.test.mybatis.vo.TestVo;

import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/10/26 15:00
 * @Description:
 */
public interface MybatisMapper extends BaseDao<TestPo> {

    String selectAnyMaster();

    String selectAnySlave();

    List<TestVo> listPage(TestVo vo);
}
