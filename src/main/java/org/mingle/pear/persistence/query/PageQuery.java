/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

import java.io.Serializable;

import org.mingle.pear.persistence.Pagination;

import lombok.Data;

/**
 * 分页查询的查询表单,包含分页查询的所需的所有信息,<T>代表需要查询的表单
 * 
 * @since 1.8
 * @author Mingle
 */
public @Data abstract class PageQuery<T> implements Serializable {
	private static final long serialVersionUID = 6418464074582318058L;

	private int currPage = 1;

	private SortOrder order = SortOrder.ASC;

	private int pageSize = Pagination.PAGE_SIZE;

	private T searchForm;

	private String sortColumn;

	public PageQuery() {
		this.searchForm = initSearchForm();
	}

	/**
	 * 初始化默认的查询表单
	 */
	protected abstract T initSearchForm();

	/**
	 * 计算需要获取的第一条记录
	 * 
	 * @return 需要获取的第一条记录
	 */
	public int getFirstResult() {
		return (currPage - 1) * pageSize;
	}

	/**
	 * 计算需要获取的最后一条记录
	 * 
	 * @return 需要获取的最后一条记录
	 */
	public int getMaxResults() {
		return pageSize;
	}

}
