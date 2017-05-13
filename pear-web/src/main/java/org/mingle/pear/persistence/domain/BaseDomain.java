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

package org.mingle.pear.persistence.domain;

import org.apache.commons.lang3.StringUtils;
import org.mingle.pear.persistence.Identifiable;

import java.io.Serializable;
import java.util.Objects;

/**
 * domain的基础类
 *
 * @author Mingle
 * @since 1.8
 */
public abstract class BaseDomain<ID extends Serializable> implements Identifiable<ID> {
    private static final long serialVersionUID = 3030311272814932991L;

    @Override
    public boolean hasId() {
        ID primaryKey = this.getId();
        if (primaryKey instanceof String) {
            return StringUtils.isNotBlank((String) primaryKey);
        }
        return primaryKey != null;
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
