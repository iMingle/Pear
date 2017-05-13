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

import javax.inject.Inject;

import org.mingle.pear.properties.PropertiesMail;
import org.mingle.pear.util.MailUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件服务配置
 *
 * @author mingle
 * @since 1.8
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
