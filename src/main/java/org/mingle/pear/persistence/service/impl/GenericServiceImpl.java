/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.mingle.pear.persistence.Identifiable;
import org.mingle.pear.persistence.Pagination;
import org.mingle.pear.persistence.PaginationBuilder;
import org.mingle.pear.persistence.dao.GenericDao;
import org.mingle.pear.persistence.query.PageQuery;
import org.mingle.pear.persistence.query.QueryTemplate;
import org.mingle.pear.persistence.query.QueryType;
import org.mingle.pear.persistence.service.GenericService;

/**
 * 泛型服务的实现类
 * 
 * @since 1.8
 * @author Mingle
 */
public abstract class GenericServiceImpl<E extends Identifiable<ID>, ID extends Serializable>
		implements GenericService<E, ID> {
	protected abstract GenericDao<E, ID> getDao();

	@Override
	public E find(ID id) {
		return getDao().find(id);
	}

	@Override
	public List<Object[]> find(QueryTemplate qt) {
		return getDao().find(qt);
	}

	@Override
	public List<Map<String, Object>> find(QueryTemplate qt, String... fileds) {
		return getDao().find(qt, fileds);
	}

	@Override
	public BigInteger findCount(QueryTemplate qt) {
		return getDao().findCount(qt);
	}

	@Override
	public List<E> findDomains(QueryTemplate qt) {
		return getDao().findDomains(qt);
	}

	@Override
	public E findFirstDomain(QueryTemplate qt) {
		return getDao().findFirstDomain(qt);
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<E> findDomainPage(
			QueryType qlType, PageQuery<S> queryPage,
			PaginationBuilder<S, P> builder) {
		return getDao().findDomainPage(qlType, queryPage, builder);
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<Object[]> findArrayPage(
			QueryType qlType, PageQuery<S> queryPage,
			PaginationBuilder<S, P> builder) {
		return getDao().findArrayPage(qlType, queryPage, builder);
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<Map<String, Object>> findMapPage(
			QueryType qlType, PageQuery<S> queryPage, String[] fields,
			PaginationBuilder<S, P> builder) {
		return getDao().findMapPage(qlType, queryPage, fields, builder);
	}

	@Override
	public E getReference(ID id) {
		return getDao().getReference(id);
	}

	@Override
	public boolean isExist(ID id) {
		return getDao().isExist(id);
	}
	
	@Override
	public boolean isExist(Class<?> resultClass, Map<String, Object> parameters) {
		return getDao().isExist(resultClass, parameters);
	}

	@Override
	public E merge(E entity) {
		return getDao().merge(entity);
	}

	@Override
	public void persist(E entity) {
		getDao().persist(entity);
	}

	@Override
	public void detach(E entity) {
		getDao().detach(entity);
	}

	@Override
	public void refresh(E entity) {
		getDao().refresh(entity);
	}

	@Override
	public void remove(E entity) {
		getDao().remove(entity);
	}

	@Override
	public void remove(ID id) {
		getDao().remove(id);
	}

	@Override
	public void removeBatch(ID[] ids) {
		getDao().removeBatch(ids);
	}

	@Override
	public void removeBatch(Collection<E> entitys) {
		getDao().removeBatch(entitys);
	}

	@Override
	public void clear() {
		getDao().clear();
	}

	@Override
	public void flush() {
		getDao().flush();
	}
}
