/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import java.util.EnumSet;

/**
 * ServletContext initializer for Spring Security specific configuration such as
 * the chain of Spring Security filters.
 */
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "/*");
        super.beforeSpringSecurityFilterChain(servletContext);
    }

}
