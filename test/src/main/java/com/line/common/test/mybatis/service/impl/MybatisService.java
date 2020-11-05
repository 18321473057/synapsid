package com.line.common.test.mybatis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.line.base.datasource.dynamic.AnotherDatasource;
import com.line.common.test.mybatis.dao.MybatisMapper;
import com.line.common.test.mybatis.po.TestPo;
import com.line.common.test.mybatis.service.IMybatisService;
import com.line.common.test.mybatis.vo.TestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: yangcs
 * @Date: 2020/10/26 14:58
 * @Description:
 */
@Service
public class MybatisService implements IMybatisService {

    @Autowired
    private MybatisMapper mybatisMapper;

    @AnotherDatasource("master")
    public String selectAnyMaster() {
        return mybatisMapper.selectAnyMaster();
    }

    @AnotherDatasource("slave")
    public String selectAnySlave() {
        return mybatisMapper.selectAnySlave();
    }


    /**
     * @warning 可悲的事情发生了, 这注解只能在service的入口方法 标注才会生效
     * <p>
     * 尝试再写一个service 来调用两次方法, 实现数据源切换
     */
    @AnotherDatasource("master")
    public String selectAnyAll() {
        String a = selectAnyMaster();
        String b = selectAnySlave();
        return a + "-" + b;
    }


    public TestPo selectOneByTKMapper(String id) {
        TestPo testPo = new TestPo();
        testPo.setId(id);
        return mybatisMapper.selectByPrimaryKey(testPo);
    }


    public int instOneByTKMapper(String id) {
        TestPo testPo = new TestPo();
        testPo.setId(id);
        testPo.setActive(1);
        testPo.setCreateTime(new Date());
        testPo.setCreateUserCode("ycs");
        testPo.setModifyTime(new Date());
        testPo.setModifyUserCode("ycs");
        return mybatisMapper.insert(testPo);
    }

    public List<TestVo> listPage(TestVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        return mybatisMapper.listPage(vo);
    }

    public PageInfo listpageInfo(TestVo vo) {
        PageHelper.startPage(vo.getPage(), vo.getRows());
        List<TestVo> testVos = mybatisMapper.listPage(vo);
        return new PageInfo(testVos);
    }


}
