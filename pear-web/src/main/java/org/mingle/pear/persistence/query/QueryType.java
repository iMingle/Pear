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
 * 查询分类
 *
 * @author mingle
 * @since 1.8
 */
public enum QueryType {
    /**
     * Java对象查询语言, JPA规范(JQL) 也支持HQL(Hibernate查询语言)
     */
    JQL,

    /**
     * ORM文件或业务对象类注册的查询语句(查询条件是固定的)
     */
    NAMED_QUERY,

    /**
     * 原生SQL查询，语法依赖于不同的数据库
     */
    SQL,

    /**
     * 将原生SQL映射为对象，详细请参考JPA规范手册
     */
    SQL_RESULT_SET_MAPPING
}
