package org.mingle.pear.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.SpringBeanPreparerFactory;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 视图解析配置
 * 
 * @since 1.8
 * @author Mingle
 */
@EnableWebMvc
@Configuration
public class WebViewConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		Map<String, MediaType> mediaTypes = new HashMap<>();
		mediaTypes.put("xml", MediaType.APPLICATION_XML);
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("html", MediaType.TEXT_HTML);
		mediaTypes.put("htm", MediaType.TEXT_HTML);
		mediaTypes.put("txt", MediaType.TEXT_PLAIN);
		ContentNegotiationManager manager = new ContentNegotiationManager(
			new HeaderContentNegotiationStrategy(), new PathExtensionContentNegotiationStrategy(mediaTypes));
		resolver.setContentNegotiationManager(manager);
		List<View> defaultViews = new ArrayList<>();
		MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();
		jackson2JsonView.setObjectMapper(new ObjectMapper());
		defaultViews.add(jackson2JsonView);
		resolver.setDefaultViews(defaultViews);
		resolver.setOrder(0);
		return resolver;
	}
	
	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
    public UrlBasedViewResolver tilesViewResolver() {
    	UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
    	tilesViewResolver.setViewClass(TilesView.class);
    	tilesViewResolver.setOrder(2);
    	return tilesViewResolver;
    }
	
	/**
     * 配置tiles布局文件
     * 
     * @return
     */
    public static TilesConfigurer tilesConfigurer() {
    	TilesConfigurer configurer = new TilesConfigurer();
    	configurer.setDefinitions("/WEB-INF/layouts/layouts.xml", "/WEB-INF/layouts/**/views.xml");
    	configurer.setPreparerFactoryClass(SpringBeanPreparerFactory.class);
    	return configurer;
    }
	
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setOrder(3);
        return resolver;
    }
	
    @Bean
	public BeanNameViewResolver beanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(4);
		return resolver;
	}
    
	@Bean
	public ResourceBundleViewResolver resourceBundleViewResolver() {
		ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
		resolver.setBasename("views"); // default value
		resolver.setOrder(5);
		return resolver;
	}
	
}
