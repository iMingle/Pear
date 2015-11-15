package org.mingle.pear.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "org.mingle.pear", excludeFilters = {
	@Filter(value = Controller.class)
})
public class ApplicationConfig {
	
}
