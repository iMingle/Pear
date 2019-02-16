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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.mingle.pear.exception.HttpRequestTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * web请求性能测试
 *
 * @author mingle
 */
@Component
@Aspect
public class RequestPerformance {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the org.mingle.pear.web package or any sub-ge
     * under that.
     */
//	@Pointcut("within(org.mingle.pear.web..*)")	// 会影响JPA的persist的执行,先改为统计service layer
    @Pointcut("within(org.mingle.pear.service.*)")
    public void performance() {
    }

    @Before("performance()")
    public void before() {
        logger.info("aop before");
    }

    @After("performance()")
    public void after() {
        logger.info("aop after");
    }

    @AfterReturning("performance()")
    public void afterReturning() {
        logger.info("aop afterReturning");
    }

    @AfterThrowing("performance()")
    public void afterThrowing() {
        logger.info("aop afterThrowing");
    }

    @Around("performance()")
    public void around(ProceedingJoinPoint joinPoint) {
        try {
            logger.info("aop around");
            long start = System.currentTimeMillis();
            joinPoint.proceed();
            long end = System.currentTimeMillis();

            if ((end = end - start) > (5 * 1000))
                throw new HttpRequestTimeoutException(end);

            logger.info(joinPoint.getSignature() + " took " + end + " milliseconds");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
