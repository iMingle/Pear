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

package org.mingle.pear.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 读取数据库的配置文件信息
 *
 * @author mingle
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
