package org.mingle.pear.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 应用程序配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@ComponentScan(basePackages = "org.mingle.pear", excludeFilters = {
	@Filter(value = Controller.class),
	@Filter(type = FilterType.REGEX, pattern = ".*_Roo_.*")
})
public class ApplicationConfig {
	
}
