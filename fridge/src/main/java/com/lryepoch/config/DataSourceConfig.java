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
 *
 * 在使用JDBC作为数据访问技术的时候，Spring Boot为我们定义了PlatformTransactionManager的实现DataSourceTransactionManager的Bean。
 * 在使用JPA作为数据库访问技术的时候，Spring Boot为我们定义了PlatformTransactionManager的实现JpaBaseConfiguration的Bean。
 *
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

    /**
    * 事务管理器
    */
    @Bean(name = "primaryTransactionManager")
    @Primary
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager getTransactionManagerForInsert() {
        return new DataSourceTransactionManager(getDataSource());
    }

    /**
    * 创建SqlSession实例的工厂。所有的MyBatis应用都是以SqlSessionFactory实例为中心，SqlSessionFactory的实例可以通过SqlSessionFactoryBuilder对象来获取。
     * 有了它以后，顾名思义，就可以通过SqlSession提供的openSession()方法来获取SqlSession实例
    */
    @Bean(name = "primarySqlSessionFactory")
    @Primary
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("primaryDataSource") DataSource datasource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(datasource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(dataSourceEntity.getMapperPath()));
        return sessionFactoryBean.getObject();
    }

}
