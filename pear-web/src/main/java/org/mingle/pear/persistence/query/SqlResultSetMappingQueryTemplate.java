/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

/**
 * 通过SQL映射创建SQL查询模板
 *
 * @author Mingle
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
