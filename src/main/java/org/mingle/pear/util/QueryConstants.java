/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.util;

/**
 * 查询常量
 * 
 * @since 1.8
 * @author Mingle
 */
public final class QueryConstants {
	/**
	 * 每次查询的最大记录数，默认100
	 */
	public static final int MAX_RESULTS = 100;

	/**
	 * 查询条件辅助语句
	 */
	public static final String WHERE = " where 1=1 ";

	/**
	 * 模糊匹配辅助符
	 */
	public static final String LIKE_FLAG = "%";

	/**
	 * 排序字段辅助语句
	 */
	public static final String ORDER_BY = " order by {0}.{1} {2} ";

	/**
	 * in查询辅助语句
	 */
	public static final String IN = " {0} in ({1}) ";

}
