/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 读取数据库的配置文件信息
 *
 * @author Mingle
 * @since 1.8
 */
@Repository
public class PropertiesDatabase {
    @Value("${database.driverClassName}")
    @Getter
    private String driverClassName;
    @Value("${database.url}")
    @Getter
    private String url;
    @Value("${database.username}")
    @Getter
    private String username;
    @Value("${database.password}")
    @Getter
    private String password;
}
