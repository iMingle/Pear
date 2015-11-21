package org.mingle.pear.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
* 任务调度配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {
	private static final Logger logger = LogManager.getLogger(ScheduleConfig.class.getName());
	
	@Scheduled(fixedDelay = 2000)
	public void test() {
		logger.info("Schedule fixedDelay 2s");
	}
	
	@Scheduled(cron = "0/3 * * * * ?")
	public void test1() {
		logger.info("Schedule every 3s");
	}
	
}
