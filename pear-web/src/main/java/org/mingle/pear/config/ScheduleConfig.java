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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 任务调度配置
 *
 * @author mingle
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {
    private static final Logger logger = LogManager.getLogger(ScheduleConfig.class.getName());

    @Scheduled(fixedDelay = 2000)
    public void test() {
        logger.debug("Schedule fixedDelay 2s");
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void test1() {
        logger.debug("Schedule every 3s");
    }

}
