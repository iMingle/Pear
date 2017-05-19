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

package org.mingle.pear.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.ContentVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * WEB MVC配置
 *
 * @author mingle
 */
@EnableWebMvc
@Configuration
@PropertySource({
        "classpath:prop/database.properties",
        "classpath:prop/mail.properties"
})
@ComponentScan(basePackages = "org.mingle.pear", includeFilters = {
        @Filter(value = Controller.class)
})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new ContentVersionStrategy(), "/**");

        registry.addResourceHandler("/selenium/**").addResourceLocations("/selenium/")
                .setCachePeriod(60 * 60).resourceChain(true).addResolver(versionResourceResolver);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/")
                .setCachePeriod(60 * 60).resourceChain(true).addResolver(versionResourceResolver);
        registry.addResourceHandler("/styles/**").addResourceLocations("/styles/")
                .setCachePeriod(60 * 60).resourceChain(true).addResolver(versionResourceResolver);
        registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/")
                .setCachePeriod(60 * 60).resourceChain(true).addResolver(versionResourceResolver);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login");
        registry.addViewController("/logoutSuccess");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ThemeChangeInterceptor());
        registry.addInterceptor(new LocaleChangeInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    @Override
    public void configureHandlerExceptionResolvers(
            List<HandlerExceptionResolver> exceptionResolvers) {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setDefaultErrorView("errors/uncaughtException");
        Properties mappings = new Properties();
        mappings.put(".DataAccessException", "errors/dataAccessFailure");
        mappings.put(".NoSuchRequestHandlingMethodException", "errors/resourceNotFound");
        mappings.put(".TypeMismatchException", "errors/resourceNotFound");
        mappings.put(".MissingServletRequestParameterException", "errors/resourceNotFound");
        resolver.setExceptionMappings(mappings);

        exceptionResolvers.add(resolver);
    }

    /**
     * Open EntityManager in View, i.e. to allow for lazy loading in web views
     * despite the original transactions already being completed.
     *
     * @return
     */
    @Bean
    public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
        return new OpenEntityManagerInViewInterceptor();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Resolves localized messages*.properties and application.properties files in the application to allow for internationalization.
     * The messages*.properties files translate Roo generated messages which are part of the admin interface, the
     * application.properties resource bundle localizes all application specific messages such as entity names and menu items.
     *
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("resources/prop");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    /**
     * Store preferred language configuration in a cookie
     *
     * @return
     */
    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("locale");
        localeResolver.setDefaultLocale(Locale.CHINESE);
        localeResolver.setCookieMaxAge(2 * 7 * 24 * 60 * 60);
        return localeResolver;
    }

    /**
     * Resolves localized <theme_name>.properties files in the classpath to
     * allow for theme support
     *
     * @return
     */
    @Bean
    public ResourceBundleThemeSource themeSource() {
        ResourceBundleThemeSource themeSource = new ResourceBundleThemeSource();
        themeSource.setDefaultEncoding("UTF-8");
        return themeSource;
    }

    /**
     * Store preferred theme configuration in a cookie
     *
     * @return
     */
    @Bean
    public CookieThemeResolver themeResolver() {
        CookieThemeResolver themeResolver = new CookieThemeResolver();
        themeResolver.setCookieName("theme");
        themeResolver.setDefaultThemeName("standard");
        themeResolver.setCookieMaxAge(2 * 7 * 24 * 60 * 60);
        return themeResolver;
    }

    /**
     * Enable this for integration of file upload functionality
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1024 * 500);
        multipartResolver.setMaxInMemorySize(1024 * 1024 * 10);
        return multipartResolver;
    }
}
