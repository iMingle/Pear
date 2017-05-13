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
