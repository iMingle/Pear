/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * 所有实体实现Comparable接口
 * 
 * @since 1.8
 * @author Mingle
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
