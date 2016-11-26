/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.persistence.query;

/**
 * 查询构造器
 *
 * @author Mingle
 * @since 1.8
 */
public interface QueryBuilder<T> {

    /**
     * 构建select子句
     *
     * @return select子句
     */
    void buildSelect(QueryTemplate qt);

    /**
     * 构建where子句
     *
     * @param t          查询对象
     * @param parameters
     * @return where子句
     */
    void buildWhere(T t, QueryTemplate qt);

    /**
     * 构建group by与order by子句
     *
     * @param column 排序列
     * @param order  排序方向
     * @return groupby与order子句
     */
    void buildBys(String column, SortOrder order, QueryTemplate qt);

}
