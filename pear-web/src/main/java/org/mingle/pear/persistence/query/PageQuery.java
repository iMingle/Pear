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

import lombok.Data;
import org.mingle.pear.persistence.Pagination;

import java.io.Serializable;

/**
 * 分页查询的查询表单,包含分页查询的所需的所有信息,<T>代表需要查询的表单
 *
 * @author Mingle
 * @since 1.8
 */
public
@Data
abstract class PageQuery<T> implements Serializable {
    private static final long serialVersionUID = 6418464074582318058L;

    private int currPage = 1;

    private SortOrder order = SortOrder.ASC;

    private int pageSize = Pagination.PAGE_SIZE;

    private T searchForm;

    private String sortColumn;

    public PageQuery() {
        this.searchForm = initSearchForm();
    }

    /**
     * 初始化默认的查询表单
     */
    protected abstract T initSearchForm();

    /**
     * 计算需要获取的第一条记录
     *
     * @return 需要获取的第一条记录
     */
    public int getFirstResult() {
        return (currPage - 1) * pageSize;
    }

    /**
     * 计算需要获取的最后一条记录
     *
     * @return 需要获取的最后一条记录
     */
    public int getMaxResults() {
        return pageSize;
    }

}
