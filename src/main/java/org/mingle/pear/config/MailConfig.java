/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import javax.inject.Inject;

import org.mingle.pear.properties.PropertiesMail;
import org.mingle.pear.util.MailUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件服务配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
public class MailConfig {
	@Inject
	private PropertiesMail propMail;
	
	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(propMail.getHost());
		mailSender.setPort(propMail.getPort());
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setProtocol(propMail.getProtocol());
		mailSender.setUsername(propMail.getUsername());
		mailSender.setPassword(MailUtils.decryptPassword(propMail.getPassword()));
		return mailSender;
	}

}
