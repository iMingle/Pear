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

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息配置
 *
 * @author mingle
 */
@Configuration
public class MessageConfig {
    /**
     * 创建连接工厂实例
     *
     * @return
     */
    @Bean
    public ActiveMQConnectionFactoryFactoryBean activeMQConnectionFactoryFactoryBean() {
        ActiveMQConnectionFactoryFactoryBean bean = new ActiveMQConnectionFactoryFactoryBean();
        bean.setTcpHostAndPort("tcp://localhost:61616");
        return bean;
    }

    /**
     * 声明ActiveMQ消息目的地
     *
     * @return
     */
    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("application.queue");
    }

    @Bean
    public ActiveMQTopic activeMQTopic() {
        return new ActiveMQTopic("application.topic");
    }
}
