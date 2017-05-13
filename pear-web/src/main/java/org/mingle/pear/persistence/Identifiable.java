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

import java.io.Serializable;

/**
 * 可持久化类主键接口
 *
 * @author mingle
 * @since 1.8
 */
public interface Identifiable<ID extends Serializable> extends Serializable {
    /**
     * 获取实体类的主键
     *
     * @return 主键
     */
    ID getId();

    /**
     * 判断主键是否已设置
     *
     * @return 主键是否已设置
     */
    boolean hasId();

    /**
     * 设置主键
     *
     * @param id 主键
     */
    void setId(ID id);
}
