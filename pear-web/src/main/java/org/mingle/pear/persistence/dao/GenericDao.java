/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.mingle.pear.persistence.Identifiable;
import org.mingle.pear.persistence.Pagination;
import org.mingle.pear.persistence.PaginationBuilder;
import org.mingle.pear.persistence.query.PageQuery;
import org.mingle.pear.persistence.query.QueryTemplate;
import org.mingle.pear.persistence.query.QueryType;

/**
 * 泛型数据库访问接口
 * 
 * @since 1.8
 * @author Mingle
 */
public interface GenericDao<E extends Identifiable<ID>, ID extends Serializable> {
	
	/**
	 * 执行各类QL语句,SqlResultMappingQueryTemplate不支持此操作
	 * 
	 * @param qt
	 */
	int execute(QueryTemplate qt);

	/**
	 * 根据主键获取对象
	 * 
	 * @param id
	 * @return
	 */
	E find(ID id);

	/**
	 * 根据查询模板进行查询,SqlResultMappingQueryTemplate不支持此操作
	 * 
	 * @param qt
	 *            查询模板
	 * @return 以Object[]表示的List对象
	 */
	List<Object[]> find(QueryTemplate qt);

	/**
	 * 根据查询模板进行查询
	 * 
	 * @param qt
	 *            查询模板
	 * @return 以Map<String, Object>表示的List对象
	 */
	List<Map<String, Object>> find(QueryTemplate qt, String... fileds);

	/**
	 * 根据查询模板进行统计查询，SqlResultMappingQueryTemplate不支持此操作
	 * 
	 * @param qt
	 *            查询模板
	 * @return 以Map<String, Object>表示的List对象
	 */
	BigInteger findCount(QueryTemplate qt);

	/**
	 * 根据查询模板进行查询
	 * 
	 * @param clazz
	 * @param qt
	 * @return 查询结果
	 */
	List<E> findDomains(QueryTemplate qt);

	/**
	 * 返回查询列表的第一个对象，如果List不为空则获取List的第一个值，否则返回null值
	 * 
	 * @param qt
	 * @return
	 */
	E findFirstDomain(QueryTemplate qt);

	/**
	 * 根据分页对象进行分页查询,仅支持HQL\JQL查询
	 * 
	 * @param qlType
	 *            查询语言的类型
	 * @param queryPage
	 *            分页对象
	 * @param builder
	 *            分页查询辅助对象
	 * @return 业务实体的分页结果
	 */
	<T, P extends PageQuery<T>> Pagination<E> findDomainPage(QueryType qlType,
			PageQuery<T> queryPage, PaginationBuilder<T, P> builder);

	/**
	 * 根据分页对象进行分页查询,仅支持HQL\JQL查询
	 * 
	 * @param qlType
	 *            查询语言的类型
	 * @param queryPage
	 *            分页对象
	 * @param builder
	 *            分页查询辅助对象
	 * @return 数组的分页结果
	 */
	<T, P extends PageQuery<T>> Pagination<Object[]> findArrayPage(
			QueryType qlType, PageQuery<T> queryPage,
			PaginationBuilder<T, P> builder);

	/**
	 * 根据分页对象进行分页查询,仅支持HQL\JQL查询
	 * 
	 * @param qlType
	 *            查询语言的类型
	 * @param queryPage
	 *            分页对象
	 * @param builder
	 *            分页查询辅助对象
	 * @return Map的分页结果
	 */
	<T, P extends PageQuery<T>> Pagination<Map<String, Object>> findMapPage(
			QueryType qlType, PageQuery<T> queryPage, String[] fields,
			PaginationBuilder<T, P> builder);

	/**
	 * 获取对象引用，能延迟对象加载
	 * 
	 * @param id
	 *            主键
	 * @return 对象的引用
	 */
	E getReference(ID id);

	/**
	 * 获取Hibernate Session
	 * 
	 * @return 返回Hibernate Session
	 */
	Session getSession();

	/**
	 * 根据主键判断是否存在此对象
	 * 
	 * @param id
	 *            主键
	 * @return pk 是否存在此主键
	 */
	boolean isExist(ID id);

	/**
	 * 根据字段名称和值判断是否存在此对象
	 * 
	 * @param parameters 字段名称和值
	 * @return
	 */
	boolean isExist(Class<?> resultClass, Map<String, Object> parameters);
	
	/**
	 * 状态合并与更新
	 * 
	 * @param entity
	 *            需要更新的实体类
	 * @return entity 更新的实体类
	 */
	E merge(E entity);

	/**
	 * 持久化当前对象
	 * 
	 * @param entity
	 *            实体
	 * @return
	 */
	void persist(E entity);

	/**
	 * 分离实体
	 * 
	 * @param entity
	 */
	void detach(E entity);
	
	/**
	 * 刷新对象状态
	 * 
	 * @param entity
	 *            待更新的实体
	 */
	void refresh(E entity);

	/**
	 * 删除当前对象，可以通过getReference获取
	 * 
	 * @param entity
	 *            待删除的对象
	 */
	void remove(E entity);

	/**
	 * 删除主键为pk的实体对象
	 * 
	 * @param id
	 *            主键
	 */
	void remove(ID id);

	/**
	 * 删除多个实体对象
	 * 
	 * @param ids
	 */
	void removeBatch(ID[] ids);

	/**
	 * 删除多个实体对象
	 * 
	 * @param entities
	 */
	void removeBatch(Collection<E> entities);
	
	/**
	 * 清理持久化上下文,导致所有的管理实体分离
	 */
	void clear();
	
	/**
	 * 同步持久化上下文到数据库
	 */
	void flush();

	/**
	 * 获取持久化类Class
	 * 
	 * @return
	 */
	public abstract Class<E> getPersistentClass();

	/**
	 * 获取持久化类的名称
	 * 
	 * @return
	 */
	public abstract String getDomainName();

}
