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

package org.mingle.pear.web;

import org.mingle.pear.domain.Account;
import org.mingle.pear.service.AccountService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * A central place to register application converters and formatters.
 *
 * @author mingle
 */
@Component("applicationConversionService")
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
    @Inject private AccountService accountService;

    public Converter<Account, String> getAccountToStringConverter() {
        return new Converter<Account, String>() {
            public String convert(Account account) {
                return new StringBuilder().append(account.getName()).append(' ').append(account.getAge()).toString();
            }
        };
    }

    public Converter<Long, Account> getIdToAccountConverter() {
        return new Converter<Long, Account>() {
            public Account convert(java.lang.Long id) {
                return accountService.find(id);
            }
        };
    }

    public Converter<String, Account> getStringToAccountConverter() {
        return new Converter<String, Account>() {
            public Account convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Account.class);
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
