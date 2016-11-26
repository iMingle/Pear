/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

import java.util.Map;

/**
 * 命名查询模板
 *
 * @author Mingle
 * @since 1.8
 */
public class NamedQueryTemplate extends QueryTemplate {

    public NamedQueryTemplate() {
        super();
    }

    public NamedQueryTemplate(String query, Map<String, Object> parameters) {
        super(query, parameters);
    }

    public NamedQueryTemplate(String query) {
        super(query);
    }

}
