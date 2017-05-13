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

package org.mingle.pear.persistence.query;

/**
 * 通过SQL映射创建SQL查询模板
 *
 * @author mingle
 * @since 1.8
 */
public class SqlResultSetMappingQueryTemplate extends QueryTemplate {
    private String sqlResultMapping;

    public SqlResultSetMappingQueryTemplate() {
        super();
    }

    /**
     * @param query 指向SqlResultMapping定义的标识符
     */
    public SqlResultSetMappingQueryTemplate(String query) {
        super(query);
    }

    public SqlResultSetMappingQueryTemplate(String query, String sqlRString) {
        this(query);
        this.sqlResultMapping = sqlRString;
    }

    public String getSqlResultMapping() {
        return sqlResultMapping;
    }

    public void setSqlResultMapping(String sqlResultMapping) {
        this.sqlResultMapping = sqlResultMapping;
    }

}
