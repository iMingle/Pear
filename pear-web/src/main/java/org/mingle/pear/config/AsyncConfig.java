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
 * @author mingle
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Async
	public Future<Long> async(Long id) {
		RestTemplate restTemplate = new RestTemplate();
		Long result = restTemplate.getForObject("https://api.github.com/users/" + id, Long.class);
		return new AsyncResult<>(result);
	}
}
