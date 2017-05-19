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

package org.mingle.pear.persistence;

import org.mingle.pear.persistence.query.PageQuery;
import org.mingle.pear.persistence.query.QueryBuilder;
import org.mingle.pear.persistence.query.QueryTemplate;

/**
 * 分页查询构造器,依次构造count,list以及where查询条件
 *
 * @author mingle
 */
public interface PaginationBuilder<S, P extends PageQuery<S>> extends
        QueryBuilder<S> {

    /**
     * 构建count子句
     *
     * @return count子句
     */
    void buildCount(QueryTemplate qt);
}
