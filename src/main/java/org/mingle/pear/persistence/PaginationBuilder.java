/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence;

import org.mingle.pear.persistence.query.PageQuery;
import org.mingle.pear.persistence.query.QueryBuilder;
import org.mingle.pear.persistence.query.QueryTemplate;

/**
 * 分页查询构造器,依次构造count,list以及where查询条件
 * 
 * @since 1.8
 * @author Mingle
 */
public interface PaginationBuilder<S, P extends PageQuery<S>> extends
		QueryBuilder<S> {

	/**
	 * 构建count子句
	 * 
	 * @return count子句
	 */
	void buildCount(QueryTemplate qt);
}
