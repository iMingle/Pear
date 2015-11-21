/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.domain;

/**
 * domain的基础类
 * 
 * @since 1.8
 * @author Mingle
 */
public class BaseDomain implements Comparable<BaseDomain> {
	private Long id;

	@Override
	public int compareTo(BaseDomain o) {
		return this.id.compareTo(o.id);
	}

}
