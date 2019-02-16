/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mingle.pear.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
import javax.sql.DataSource;

/**
 * 数据访问配置
 *
 * @author mingle
 */
@Configuration
@EnableTransactionManagement
@ManagedResource(description = "DataSource Manager.")
@MapperScan("org.mingle.pear.dao")
public class DataAccessMybatisConfig {
    @Inject private PropertiesDatabase propDatabase;

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig(propDatabase.getUrl(), propDatabase.getUsername(),
                propDatabase.getPassword()));
    }

    private HikariConfig hikariConfig(String url, String userName, String password) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(propDatabase.getDriverClassName());
        config.setAutoCommit(true);
        config.setConnectionTestQuery("SELECT 1");
        config.setMaximumPoolSize(100);
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        config.setConnectionTimeout(10000);
        config.setMaxLifetime(180000);
        return config;
    }

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
        mapperScannerConfigurer.setBasePackage("org.mingle.pear.dao");
        return mapperScannerConfigurer;
    }

}
