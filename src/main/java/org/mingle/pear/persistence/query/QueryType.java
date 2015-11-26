/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

/**
 * 查询分类
 * 
 * @since 1.8
 * @author Mingle
 */
public enum QueryType {
	/**
	 * Java对象查询语言, JPA规范(JQL) 也支持HQL(Hibernate查询语言)
	 */
	JQL,

	/**
	 * ORM文件或业务对象类注册的查询语句(查询条件是固定的)
	 */
	NAMED_QUERY,

	/**
	 * 原生SQL查询，语法依赖于不同的数据库
	 */
	SQL,

	/**
	 * 将原生SQL映射为对象，详细请参考JPA规范手册
	 */
	SQL_RESULT_SET_MAPPING
}
