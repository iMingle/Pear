/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * 异步任务配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Async
	public Future<Long> async(Long id) throws InterruptedException {
		RestTemplate restTemplate = new RestTemplate();
		Long result = restTemplate.getForObject("https://api.github.com/users/" + id, Long.class);
		return new AsyncResult<Long>(result);
	}
}
