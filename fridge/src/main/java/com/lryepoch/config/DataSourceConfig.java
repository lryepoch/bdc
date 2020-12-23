package com.lryepoch.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lryepoch.config.entity.DataSourceEntity;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author lryepoch
 * @date 2020/9/29 14:35
 * @description TODO 自定义数据库连接配置
 */
@Configuration
@MapperScan(basePackages = "com.lryepoch.dao", sqlSessionFactoryRef = "primarySqlSessionFactory")
public class DataSourceConfig {
    /**
     * 注入datasource的Bean，从配置文件读数据入该Bean
     */
    private final DataSourceEntity dataSourceEntity;

    public DataSourceConfig(DataSourceEntity dataSourceEntity) {
        this.dataSourceEntity = dataSourceEntity;
    }

    @Bean(name = "primaryDataSource")
    @Primary
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dataSourceEntity.getUrl());
        dataSource.setUsername(dataSourceEntity.getUserName());
        dataSource.setPassword(dataSourceEntity.getPassword());
        dataSource.setDriverClassName(dataSourceEntity.getDriverName());
        return dataSource;
    }

    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManagerForInsert() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("primaryDataSource") DataSource datasource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(datasource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceEntity.getMapperPath()));
        return sessionFactoryBean.getObject();
    }

}
