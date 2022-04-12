package com.line.common.test.test;

import com.line.base.dao.linestarterdaobean.sql.DdlSqlConstants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Author: yangcs
 * @Date: 2021/3/11 16:10
 * @Description:
 */
@Component
public class AAA implements ApplicationContextAware, InitializingBean {
    ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        DataSource bean = (DataSource) applicationContext.getBean("dynamicDataSource");
//        PreparedStatement ps = bean.getConnection().prepareStatement(DdlSqlConstants.ALL_TABLENAME);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            String string1 = resultSet.getString(0);
//            String string = resultSet.getString(1);
//        }



        Statement statement = bean.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(DdlSqlConstants.ALL_TABLENAME);
        while (rs.next()) {
            String name = rs.getString("TABLE_NAME");
            System.out.println(name);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
