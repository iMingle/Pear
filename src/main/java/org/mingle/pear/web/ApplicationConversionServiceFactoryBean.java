/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.web;

import javax.inject.Inject;

import org.mingle.pear.domain.Account;
import org.mingle.pear.service.AccountService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Component;

/**
 * A central place to register application converters and formatters.
 * 
 * @since 1.8
 * @author Mingle
 */
@Component("applicationConversionService")
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {
	@Inject
	private AccountService accountService;

	public Converter<Account, String> getAccountToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<org.mingle.pear.domain.Account, java.lang.String>() {
			public String convert(Account account) {
				return new StringBuilder().append(account.getName())
						.append(' ').append(account.getAge()).toString();
			}
		};
	}

	public Converter<Long, Account> getIdToAccountConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.mingle.pear.domain.Account>() {
			public org.mingle.pear.domain.Account convert(java.lang.Long id) {
				return accountService.find(id);
			}
		};
	}

	public Converter<String, Account> getStringToAccountConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, org.mingle.pear.domain.Account>() {
			public org.mingle.pear.domain.Account convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class),
						Account.class);
			}
		};
	}

	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(getAccountToStringConverter());
		registry.addConverter(getIdToAccountConverter());
		registry.addConverter(getStringToAccountConverter());
	}

	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		installLabelConverters(getObject());
	}
}
