/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.domain;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.mingle.pear.persistence.Identifiable;

/**
 * domain的基础类
 * 
 * @since 1.8
 * @author Mingle
 */
public abstract class BaseDomain<ID extends Serializable> implements Identifiable<ID> {
	private static final long serialVersionUID = 3030311272814932991L;

	@Override
	public boolean hasId() {
		ID primaryKey = this.getId();
		if (primaryKey instanceof String) {
			return StringUtils.isNotBlank((String) primaryKey);
		}
		return primaryKey != null;
	}

	@Override
	public boolean equals(Object obj) {
		return Objects.equals(this, obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this);
	}

	@Override
	public String toString() {
		return Objects.toString(this);
	}
}
