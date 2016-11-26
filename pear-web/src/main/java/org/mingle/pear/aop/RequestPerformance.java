/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mingle.pear.exception.HttpRequestTimeoutException;
import org.springframework.stereotype.Component;

/**
 * web请求性能测试
 *
 * @author Mingle
 * @since 1.8
 */
@Component
@Aspect
public class RequestPerformance {
    private static final Logger logger = LogManager.getLogger(RequestPerformance.class.getName());

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
