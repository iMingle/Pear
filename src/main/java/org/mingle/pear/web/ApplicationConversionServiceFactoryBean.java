package org.mingle.pear.web;

import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.annotations.converter.RooConversionService;
import org.springframework.stereotype.Component;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
@Component("applicationConversionService")
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

}
