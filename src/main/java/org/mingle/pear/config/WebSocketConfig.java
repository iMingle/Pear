/**
 * Copyright (c) 2015, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * websocket配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic", "/queue");	// Simple Broker
//		config.enableStompBrokerRelay("/topic", "/queue");	// Full-Featured Broker for Use ActiveMQ etc.
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/greeting").withSockJS()
			.setHeartbeatTime(5 * 1000)
			.setDisconnectDelay(3 * 1000);
	}
}
