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

package org.mingle.pear.util;

/**
 * 查询常量
 *
 * @author mingle
 * @since 1.8
 */
public final class QueryConstants {
    /**
     * 每次查询的最大记录数，默认100
     */
    public static final int MAX_RESULTS = 100;

    /**
     * 查询条件辅助语句
     */
    public static final String WHERE = " where 1=1 ";

    /**
     * 模糊匹配辅助符
     */
    public static final String LIKE_FLAG = "%";

    /**
     * 排序字段辅助语句
     */
    public static final String ORDER_BY = " order by {0}.{1} {2} ";

    /**
     * in查询辅助语句
     */
    public static final String IN = " {0} in ({1}) ";

}
