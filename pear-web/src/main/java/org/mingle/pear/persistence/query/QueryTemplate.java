/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mingle.pear.persistence.query;

import java.text.MessageFormat;
import java.util.Map;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.mingle.pear.util.BaseConstants;
import org.mingle.pear.util.QueryConstants;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 查询模板的抽象类
 *
 * @author mingle
 * @since 1.8
 */
public
@Data
abstract class QueryTemplate {
    /**
     * 查询缓存
     */
    private boolean cachable;

    /**
     * 查询缓存区域
     */
    private String cacheRegion;

    /**
     * 抓取第一条记录的位置
     */
    private int firstResult = 0;

    /**
     * 最大记录的位置
     */
    private int maxResults = QueryConstants.MAX_RESULTS;

    /**
     * 查询参数
     */
    private Map<String, Object> parameters;

    private StringBuilder query;

    /**
     * 添加SQL语句到query中
     *
     * @param sql
     */
    public QueryTemplate append(String sql) {
        Preconditions.checkArgument(
                !(this instanceof NamedQueryTemplate || this instanceof SqlResultSetMappingQueryTemplate),
                "this template can't add sql to query");
        if (query == null) {
            query = new StringBuilder();
        }
        query.append(sql);
        return this;
    }

    /**
     * 添加in查询语句
     *
     * @param sql
     * @param params
     */
    public void appendIn(String sql, Iterable<?> params) {
        query.append(MessageFormat.format(QueryConstants.IN, new Object[]{
                sql, Joiner.on(BaseConstants.SPLITER_FLAG).join(params)}));
    }

    /**
     * 创建查询模板实例
     *
     * @param type 查询模板类型
     * @return 模板实例
     */
    public static QueryTemplate create(QueryType type) {
        QueryTemplate qt = null;
        switch (type) {
            case JQL:
                qt = new JqlQueryTemplate();
                break;
            case NAMED_QUERY:
                qt = new NamedQueryTemplate();
                break;
            case SQL:
                qt = new SqlQueryTemplate();
                break;
            case SQL_RESULT_SET_MAPPING:
                qt = new SqlResultSetMappingQueryTemplate();
                break;
        }
        return qt;
    }

    /**
     * 创建查询模板实例
     *
     * @param type  查询模板类型
     * @param query 查询语句
     * @return 模板实例
     */
    public static QueryTemplate create(QueryType type, String query) {
        QueryTemplate qt = null;
        switch (type) {
            case JQL:
                qt = new JqlQueryTemplate(query);
                break;
            case NAMED_QUERY:
                qt = new NamedQueryTemplate(query);
                break;
            case SQL:
                qt = new SqlQueryTemplate(query);
                break;
            case SQL_RESULT_SET_MAPPING:
                qt = new SqlResultSetMappingQueryTemplate(query);
                break;
        }
        return qt;
    }

    public QueryTemplate() {
    }

    /**
     * 通过查询语句构造查询模板
     *
     * @param query
     */
    public QueryTemplate(final String query) {
        Preconditions.checkArgument(StringUtils.isNotBlank(query),
                "sql can't be null!");
        this.setQuery(query);
    }

    /**
     * 通过查询语句与查询参数构造查询模板
     *
     * @param parameters
     * @param query
     */
    public QueryTemplate(String query, Map<String, Object> parameters) {
        this(query);
        this.parameters = parameters;
    }

    /**
     * 是否包含查询语句
     *
     * @return
     */
    public boolean isEmpty() {
        return query == null || StringUtils.isBlank(query.toString());
    }

    /**
     * 重新构造查询模板
     *
     * @param s
     * @param builder
     */
    public <T> void rebuild(T t, String sortColumn, SortOrder order,
                            QueryBuilder<T> builder) {
        builder.buildSelect(this);
        this.append(QueryConstants.WHERE);
        builder.buildWhere(t, this);
        this.append(" ");
        builder.buildBys(sortColumn, order, this);
    }

    /**
     * 添加查询参数
     *
     * @param key
     * @param value
     */
    public void addParameter(String key, Object value) {
        if (parameters == null) {
            parameters = Maps.newHashMap();
        }
        parameters.put(key, value);
    }

    /**
     * 删除查询参数
     *
     * @param key 查询参数
     */
    public void removeParameter(String key) {
        if (parameters != null) {
            parameters.remove(key);
        }
    }

    /**
     * 获取查询语句
     *
     * @return 查询对象
     */
    public String getQuery() {
        return query.toString();
    }

    /**
     * 设置查询语句,目前只支持SQL\HQL\JQL
     *
     * @param query
     */
    public void setQuery(String query) {
        this.query = new StringBuilder(query);
    }

    /**
     * 左模糊匹配
     *
     * @param prop 需要匹配的字段名
     * @return 左模糊匹配
     */
    public static String buildLeftLike(String prop) {
        return QueryConstants.LIKE_FLAG + prop;
    }

    /**
     * 右模糊匹配
     *
     * @param prop 需要匹配的字段名
     * @return 右模糊匹配
     */
    public static String buildRightLike(String prop) {
        return prop + QueryConstants.LIKE_FLAG;
    }

    /**
     * 全模糊匹配
     *
     * @param prop 需要匹配的字段名
     * @return 全模糊匹配
     */
    public static String buildAllLike(String prop) {
        return QueryConstants.LIKE_FLAG + prop + QueryConstants.LIKE_FLAG;
    }

    /**
     * 构建order by子句
     *
     * @param alias  别名
     * @param column 列名
     * @param order  顺序
     * @return order by子句
     */
    public static String buildOrderBy(String alias, String column,
                                      SortOrder order) {
        if (StringUtils.isNotBlank(column)) {
            return MessageFormat.format(QueryConstants.ORDER_BY, alias, column,
                    order.name());
        }
        return StringUtils.EMPTY;
    }

}
