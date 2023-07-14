package com.line.common.bloomfiltermanage.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Author: yangcs
 * @Date: 2020/12/4 13:53
 * @Description: sql 执行者
 */

@Component
public class SqlExeuter {

    private static final Logger logger = LoggerFactory.getLogger(SqlExeuter.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Set<String> executeSql(String sql) {
        //     TODO    sql 应该分页

        List<String> maps = jdbcTemplate.queryForList(sql, String.class);
        logger.info("初始化过滤器元素,sql=[{}],统计查询到[{}]数据" , sql, maps.size());
        return new TreeSet<>(maps);
    }

}
