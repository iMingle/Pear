/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.mingle.pear.properties.PropertiesDatabase;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;

/**
 * 数据访问配置
 *
 * @author Mingle
 * @since 1.8
 */
@Configuration
@EnableTransactionManagement
@ManagedResource(description = "DataSource Manager.")
@MapperScan("org.mingle.pear.domain.mapper")
public class DataAccessMybatisConfig extends DataAccessConfig {
    @Inject private PropertiesDatabase propDatabase;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean sqlSession = new SqlSessionFactoryBean();
        sqlSession.setDataSource(dataSource());
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSession.setConfiguration(configuration);

        return sqlSession;
    }

    public DataSourceTransactionManager transactionManagerMybatis() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("org.mingle.pear.domain.mapper");
        return mapperScannerConfigurer;
    }

    @Override
    public PropertiesDatabase getPropDatabase() {
        return propDatabase;
    }
}
