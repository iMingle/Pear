/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;

/**
 * DispatcherServlet初始化
 *
 * @author Mingle
 * @since 1.8
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                SecurityConfig.class,
                DataAccessJpaConfig.class,
                WebMvcConfig.class,
                ApplicationConfig.class,
                WebViewConfig.class,
                MessageConfig.class,
                MailConfig.class,
                ScheduleConfig.class,
                AsyncConfig.class,
                AspectJConfig.class,
                WebSocketConfig.class,
                MBeanConfig.class,
                ContainerConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new HiddenHttpMethodFilter(),
                new OpenEntityManagerInViewFilter(),
                new ResourceUrlEncodingFilter()
        };
    }

    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        /* Enable escaping of form submission contents */
        servletContext.setInitParameter("defaultHtmlEscape", "true");
        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
        sessionCookieConfig.setMaxAge(60 * 10);
        super.onStartup(servletContext);
    }

}
