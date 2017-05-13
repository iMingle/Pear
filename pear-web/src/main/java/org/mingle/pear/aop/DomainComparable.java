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

package org.mingle.pear.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * 所有实体实现Comparable接口
 *
 * @author mingle
 * @since 1.8
 */
@Component
@Aspect
public class DomainComparable {
    /**
     * For test
     */
    @DeclareParents(value = "org.mingle.pear.domain.*", defaultImpl = NewFunctionImpl.class)
    public static Comparable<NewFunction> comparable;
}
