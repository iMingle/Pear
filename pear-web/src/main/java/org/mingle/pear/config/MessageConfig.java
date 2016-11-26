/**
 * Copyright (c) 2016, Mingle. All rights reserved.
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
 * @author Mingle
 * @since 1.8
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
