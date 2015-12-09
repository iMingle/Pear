/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.google.common.base.MoreObjects;

/**
 * 分页构造器,用于返回分页查询的结果T代表返回的数据类型,可以是Object[]\POJO\Map
 * 
 * @since 1.8
 * @author Mingle
 */
public @Data class Pagination<T> implements Serializable {
	private static final long serialVersionUID = 6712254388402592835L;

	public static final int PAGE_SIZE = 15;

	private int count;

	private int currPage = 1;

	private int pageSize = PAGE_SIZE;

	private List<T> results;

	/**
	 * 计算共有多少页
	 * 
	 * @return 页数
	 */
	public int getPages() {
		if (this.count % this.pageSize == 0) {
			return this.count / this.pageSize;
		}
		return this.count / this.pageSize + 1;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("count", this.count)
				.add("currPage", this.currPage).add("pageSize", this.pageSize)
				.toString();
	}
}
