/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

import java.util.Map;

/**
 * Java查询语言模板
 * 
 * @since 1.8
 * @author Mingle
 */
public class JqlQueryTemplate extends QueryTemplate {

	public JqlQueryTemplate() {
		super();
	}

	public JqlQueryTemplate(String query, Map<String, Object> parameters) {
		super(query, parameters);
	}

	public JqlQueryTemplate(String query) {
		super(query);
	}

}
