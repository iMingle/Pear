/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.mingle.pear.persistence.domain.BaseDomain;
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
	@DeclareParents(value = "org.mingle.pear.domain.*", defaultImpl = BaseDomain.class)
	public static Comparable<Integer> comparable;
}
