/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.mingle.pear.persistence.Identifiable;
import org.mingle.pear.persistence.Pagination;
import org.mingle.pear.persistence.PaginationBuilder;
import org.mingle.pear.persistence.dao.GenericDao;
import org.mingle.pear.persistence.query.JqlQueryTemplate;
import org.mingle.pear.persistence.query.NamedQueryTemplate;
import org.mingle.pear.persistence.query.PageQuery;
import org.mingle.pear.persistence.query.QueryTemplate;
import org.mingle.pear.persistence.query.QueryType;
import org.mingle.pear.persistence.query.SqlQueryTemplate;
import org.mingle.pear.persistence.query.SqlResultSetMappingQueryTemplate;
import org.mingle.pear.util.BaseConstants;
import org.mingle.pear.util.QueryConstants;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 基于Hibernate的JPA实现
 * 
 * @since 1.8
 * @author Mingle
 */
public class GenericDaoImpl<E extends Identifiable<ID>, ID extends Serializable>
		implements GenericDao<E, ID> {
	private final Class<E> persistentClass;

	@PersistenceContext(unitName = BaseConstants.PERSIST_UNIT_NAME, type = PersistenceContextType.TRANSACTION)
	protected EntityManager entityManager;
	
	public final EntityManager entityManager() {
		if (entityManager == null)
			throw new IllegalStateException("Entity manager has not been injected.");
        return entityManager;
	}

	@Override
	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	@Override
	public String getDomainName() {
		return persistentClass.getName();
	}

	public GenericDaoImpl(final Class<E> persistentClass) {
		super();
		this.persistentClass = persistentClass;
	}

	/**
	 * 根据查询模板创建查询对象
	 * 
	 * @param qt
	 * @param clazz
	 * @return
	 */
	protected <R> Query createQuery(QueryTemplate qt, Class<R> clazz) {
		Query query = null;
		if (qt instanceof JqlQueryTemplate) {
			if (clazz == null) {
				query = entityManager().createQuery(qt.getQuery());
			} else {
				query = entityManager().createQuery(qt.getQuery(), clazz);
			}
		}
		if (qt instanceof NamedQueryTemplate) {
			if (clazz == null) {
				query = entityManager().createNamedQuery(qt.getQuery());
			} else {
				query = entityManager().createNamedQuery(qt.getQuery(), clazz);
			}
		}
		if (qt instanceof SqlQueryTemplate) {
			query = entityManager().createNativeQuery(qt.getQuery());
		}
		if (qt instanceof SqlResultSetMappingQueryTemplate) {
			SqlResultSetMappingQueryTemplate sqt = (SqlResultSetMappingQueryTemplate) qt;
			query = entityManager().createNativeQuery(qt.getQuery(),
					sqt.getSqlResultMapping());
		}
		// 拼接查询条件
		if (qt.getParameters() != null) {
			for (Entry<String, ?> paramEntry : qt.getParameters().entrySet()) {
				query.setParameter(paramEntry.getKey(), paramEntry.getValue());
			}
		}
		// 构建查询缓存
		if (qt.isCachable()) {
			query.setHint("org.hibernate.cacheable", true);
			if (StringUtils.isNotBlank(qt.getCacheRegion())) {
				query.setHint("org.hibernate.cacheRegion", qt.getCacheRegion());
			} else {
				query.setHint("org.hibernate.cacheRegion", clazz.getName());
			}
		}
		// 构建分页
		query.setFirstResult(qt.getFirstResult());
		query.setMaxResults(qt.getMaxResults());
		return query;
	}

	@Override
	@Transactional(readOnly = false)
	public int execute(QueryTemplate qt) {
		Preconditions.checkArgument(
				!(qt instanceof SqlResultSetMappingQueryTemplate),
				"SqlResultMappingQueryTemplate can't execute update");
		return createQuery(qt, null).executeUpdate();
	}

	@Override
	public List<Object[]> find(QueryTemplate qt) {
		Preconditions.checkArgument(
				!(qt instanceof SqlResultSetMappingQueryTemplate),
				"SqlResultMappingQueryTemplate can't return object[]");
		Query query = createQuery(qt, Object[].class);
		// 获取返回的结果
		@SuppressWarnings("unchecked")
		List<Object> results = query.getResultList();
		// 返回的结果
		List<Object[]> returnVals = Lists.newArrayListWithCapacity(results
				.size());
		// 统一查询结果，都封装为List<Object[]>形式
		for (Object row : results) {
			if (row.getClass().isArray()) {
				returnVals.add((Object[]) row);
			} else {
				// 将返回的结果转换为数组
				returnVals.add(new Object[] { row });
			}
		}
		return returnVals;
	}

	@Override
	public List<Map<String, Object>> find(QueryTemplate qt,
			final String... fileds) {
		List<Object[]> results = find(qt);
		// 将数组转换为Map
		return Lists.transform(results,
			new Function<Object[], Map<String, Object>>() {
				/**
				 * 将数组转换为Map
				 */
				@Override
				public Map<String, Object> apply(Object[] arg0) {
					Map<String, Object> item = Maps.newHashMap();
					for (int i = 0; i < arg0.length; i++) {
						item.put(fileds[i], arg0[i]);
					}
					return item;
				}
			});
	}

	@Override
	public BigInteger findCount(QueryTemplate qt) {
		Preconditions.checkArgument(
				!(qt instanceof SqlResultSetMappingQueryTemplate),
				"SqlResultMappingQueryTemplate can't use for count");
		List<Object[]> results = find(qt);
		if (results.size() > 0) {
			Object count = results.get(0)[0];
			// JPA查询一般返回此结果
			if (count instanceof Long) {
				return BigInteger.valueOf((Long) count);
			}
			// 原生SQL查询一般返回此结果
			if (count instanceof BigInteger) {
				return (BigInteger) count;
			}
		}
		return BigInteger.ZERO;
	}

	@Override
	public E find(ID id) {
		if (id == null) return null;
		return entityManager().find(persistentClass, id);
	}

	@Override
	public List<E> findDomains(QueryTemplate qt) {
		Preconditions.checkArgument(!(qt instanceof SqlQueryTemplate),
				"SqlgQueryTemplate can't find Generic domain");
		Query query = createQuery(qt, persistentClass);
		
		@SuppressWarnings("unchecked")
		List<E> result = query.getResultList();
		return result;
	}

	@Override
	public E findFirstDomain(QueryTemplate qt) {
		Preconditions.checkArgument(!(qt instanceof SqlQueryTemplate),
				"SqlgQueryTemplate can't find Generic domain");
		qt.setMaxResults(1);
		List<E> results = findDomains(qt);
		if (results.size() > 0) {
			return results.get(0);
		}
		// 默认返回为空
		return null;
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<E> findDomainPage(
			QueryType qlType, PageQuery<S> queryPage,
			PaginationBuilder<S, P> builder) {
		Pagination<E> pagination = new Pagination<E>();
		// 创建分页的总记录数
		pagination.setCount(findCountOfPages(qlType, queryPage, builder));
		QueryTemplate resultQt = buildResultOfPages(qlType, queryPage, builder);
		// 设置分页返回结果
		pagination.setResults(findDomains(resultQt));
		pagination.setCurrPage(queryPage.getCurrPage());
		// 设置页容量
		pagination.setPageSize(queryPage.getPageSize());
		return pagination;
	}

	/**
	 * 构建分页查询的结果查询
	 * 
	 * @param qlType
	 * @param queryPage
	 * @param builder
	 * @return
	 */
	private <S, P extends PageQuery<S>> QueryTemplate buildResultOfPages(
			QueryType qlType, PageQuery<S> queryPage,
			PaginationBuilder<S, P> builder) {
		// 设置结果查询模板
		QueryTemplate resultQt = QueryTemplate.create(qlType);
		// 设置分页信息
		resultQt.setFirstResult(queryPage.getFirstResult());
		resultQt.setMaxResults(queryPage.getMaxResults());
		// 编译查询语句
		builder.buildSelect(resultQt);
		resultQt.append(QueryConstants.WHERE);
		builder.buildWhere(queryPage.getSearchForm(), resultQt);
		builder.buildBys(queryPage.getSortColumn(), queryPage.getOrder(),
				resultQt);
		return resultQt;
	}

	/**
	 * 构建分页计数的查询部分
	 * 
	 * @param qlType
	 * @param queryPage
	 * @param builder
	 * @param pagination
	 */
	private <S, P extends PageQuery<S>> int findCountOfPages(QueryType qlType,
			PageQuery<S> queryPage, PaginationBuilder<S, P> builder) {
		// 设置计数查询模板
		QueryTemplate countQt = QueryTemplate.create(qlType);
		builder.buildCount(countQt);
		// 编译查询语句
		if (countQt.isEmpty()) {
			return 0;
		}
		countQt.append(QueryConstants.WHERE);
		builder.buildWhere(queryPage.getSearchForm(), countQt);
		builder.buildBys(queryPage.getSortColumn(), queryPage.getOrder(),
				countQt);
		// 设置计数结果
		return findCount(countQt).intValue();
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<Object[]> findArrayPage(
			QueryType qlType, PageQuery<S> queryPage,
			PaginationBuilder<S, P> builder) {
		Pagination<Object[]> pagination = new Pagination<Object[]>();
		// 设置查询的总记录条数
		pagination.setCount(findCountOfPages(qlType, queryPage, builder));
		// 设置分页返回结果
		QueryTemplate resultQt = buildResultOfPages(qlType, queryPage, builder);
		pagination.setResults(find(resultQt));
		pagination.setCurrPage(queryPage.getCurrPage());
		return pagination;
	}

	@Override
	public <S, P extends PageQuery<S>> Pagination<Map<String, Object>> findMapPage(
			QueryType qlType, PageQuery<S> queryPage, String[] fields,
			PaginationBuilder<S, P> builder) {
		Pagination<Map<String, Object>> pagination = new Pagination<Map<String, Object>>();
		// 设置查询的总记录条数
		pagination.setCount(findCountOfPages(qlType, queryPage, builder));
		// 设置分页返回结果
		QueryTemplate resultQt = buildResultOfPages(qlType, queryPage, builder);
		pagination.setResults(find(resultQt, fields));
		pagination.setCurrPage(queryPage.getCurrPage());
		return pagination;
	}

	@Override
	public E getReference(ID id) {
		return entityManager().getReference(persistentClass, id);
	}

	@Override
	public Session getSession() {
		return entityManager().unwrap(Session.class);
	}

	@Override
	public boolean isExist(ID id) {
		return getReference(id) != null;
	}

	@Override
	@Transactional
	public E merge(E entity) {
		return entityManager().merge(entity);
	}

	@Override
	@Transactional
	public void persist(E entity) {
		if (!entity.hasId()) {
			entityManager().persist(entity);
		}
	}

	@Override
	public void detach(E entity) {
		if (entityManager().contains(entity)) {
			entityManager().detach(entity);
		}
	}

	@Override
	@Transactional
	public void refresh(E entity) {
		if (entityManager().contains(entity)) {
			entityManager().refresh(entity);
		}
	}

	@Override
	@Transactional
	public void remove(E entity) {
		if (entityManager().contains(entity)) {
			entityManager().remove(entity);
		} else {
			remove(entity.getId());
		}
	}

	@Override
	@Transactional
	public void remove(ID id) {
		E entityRef = getReference(id);
		if (entityRef != null) {
			entityManager().remove(entityRef);
		}
	}

	@Override
	@Transactional
	public void removeBatch(ID[] ids) {
		if (ids != null) {
			for (ID id : ids) {
				remove(id);
			}
		}
	}

	@Override
	@Transactional
	public void removeBatch(Collection<E> entitys) {
		if (entitys != null) {
			for (E e : entitys) {
				remove(e);
			}
		}
	}

	@Override
	@Transactional
	public void clear() {
		entityManager().clear();
	}

	@Override
	@Transactional
	public void flush() {
		entityManager().flush();
	}

}
