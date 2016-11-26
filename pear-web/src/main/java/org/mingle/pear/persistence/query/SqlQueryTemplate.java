/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

/**
 * SQL查询模板,支持用于JPA的SQL查询
 *
 * @author Mingle
 * @since 1.8
 */
public class SqlQueryTemplate extends QueryTemplate {

    public SqlQueryTemplate() {
        super();
    }

    public SqlQueryTemplate(String query) {
        super(query);
    }

}
