package org.mingle.pear.config;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableWebMvc
@Configuration
@PropertySource("classpath:META-INF/spring/database.properties")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/", "classpath:/META-INF/web-resources/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/");
		registry.addViewController("/login");
		registry.addViewController("/uncaughtException");
		registry.addViewController("/resourceNotFound");
		registry.addViewController("/dataAccessFailure");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ThemeChangeInterceptor());
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	@Bean  
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
	
	/**
     * 必须加上static
     */
    @Bean
    public static PropertyPlaceholderConfigurer loadProperties() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        return configurer;
    }
    
    /**
     * Resolves localized messages*.properties and application.properties files in the application to allow for internationalization. 
     * The messages*.properties files translate Roo generated messages which are part of the admin interface, the 
     * application.properties resource bundle localizes all application specific messages such as entity names and menu items.
     * 
     * @return
     */
    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    	messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/application");
    	messageSource.setFallbackToSystemLocale(false);
    	return messageSource;
    }
    
    /**
     * Store preferred language configuration in a cookie
     * 
     * @return
     */
    @Bean
    public CookieLocaleResolver cookieLocaleResolver() {
    	CookieLocaleResolver resolver = new CookieLocaleResolver();
    	resolver.setCookieName("locale");
    	return resolver;
    }
    
    /**
     * Resolves localized <theme_name>.properties files in the classpath to 
     * allow for theme support
     * 
     * @return
     */
    @Bean
    public ResourceBundleThemeSource resourceBundleThemeSource() {
    	return new ResourceBundleThemeSource();
    }
    
    /**
     * Store preferred theme configuration in a cookie
     * 
     * @return
     */
    @Bean
    public CookieThemeResolver cookieThemeResolver() {
    	CookieThemeResolver resolver = new CookieThemeResolver();
    	resolver.setCookieName("theme");
    	resolver.setDefaultThemeName("standard");
    	return resolver;
    }
    
    /**
     * This bean resolves specific types of exceptions to corresponding logical - view names for error views. 
     * The default behaviour of DispatcherServlet - is to propagate all exceptions to the servlet 
     * container: this will happen - here with all other types of exceptions.
     * 
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
    	SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
    	resolver.setDefaultErrorView("uncaughtException");
    	Properties mappings = new Properties();
    	mappings.put(".DataAccessException", "dataAccessFailure");
    	mappings.put(".NoSuchRequestHandlingMethodException", "resourceNotFound");
    	mappings.put(".TypeMismatchException", "resourceNotFound");
    	mappings.put(".MissingServletRequestParameterException", "resourceNotFound");
    	resolver.setExceptionMappings(mappings);
    	return resolver;
    }
    
    /**
     * Enable this for integration of file upload functionality
     * 
     * @return
     */
    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
    	return new CommonsMultipartResolver();
    }
    
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
    	UrlBasedViewResolver resolver = new UrlBasedViewResolver();
    	resolver.setViewClass(TilesView.class);
    	return resolver;
    }
    
    @Bean
    public static TilesConfigurer tilesConfigurer() {
    	TilesConfigurer configurer = new TilesConfigurer();
    	configurer.setDefinitions("/WEB-INF/**/*.xml");
    	return configurer;
    }
}
