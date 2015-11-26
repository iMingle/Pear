/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

/**
 * SQL查询模板,支持用于JPA的SQL查询
 * 
 * @since 1.8
 * @author Mingle
 */
public class SqlQueryTemplate extends QueryTemplate {

	public SqlQueryTemplate() {
		super();
	}

	public SqlQueryTemplate(String query) {
		super(query);
	}

}
