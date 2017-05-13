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

import org.apache.commons.dbcp2.BasicDataSource;
import org.mingle.pear.properties.PropertiesDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;

import javax.sql.DataSource;

/**
 * 数据访问配置
 *
 * @author Mingle
 * @since 1.8
 */
public abstract class DataAccessConfig {
    private int initialSize = 5;

    @ManagedAttribute(description = "The initialSize of connection pool.")
    public int getInitialSize() {
        return initialSize;
    }

    @ManagedOperation(description = "Change database connection pool initialSize.")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "initialSize", description = "connection pool initialSize.")
    })
    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public abstract PropertiesDatabase getPropDatabase();

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(getPropDatabase().getDriverClassName());
        dataSource.setUrl(getPropDatabase().getUrl());
        dataSource.setUsername(getPropDatabase().getUsername());
        dataSource.setPassword(getPropDatabase().getPassword());
        dataSource.setInitialSize(getInitialSize());
        dataSource.setTestOnBorrow(true);
        dataSource.setTestOnReturn(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(1800000L);
        dataSource.setNumTestsPerEvictionRun(3);
        dataSource.setMinEvictableIdleTimeMillis(1800000L);
        return dataSource;
    }

}
