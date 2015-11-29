package org.mingle.pear.config;

import java.util.Collections;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mingle.pear.properties.PropertiesDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据访问配置
 * 
 * @since 1.8
 * @author Mingle
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages="org.mingle.pear")
public class DataAccessConfig {
	@Inject
	private PropertiesDatabase propDatabase;

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPersistenceUnitName("persistenceUnit");
		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactory.setJpaPropertyMap(Collections.singletonMap("hibernate.session_factory_name", "sessionFactory"));
		return entityManagerFactory;
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(propDatabase.getDriverClassName());
		dataSource.setUrl(propDatabase.getUrl());
		dataSource.setUsername(propDatabase.getUsername());
		dataSource.setPassword(propDatabase.getPassword());
		dataSource.setInitialSize(5);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000L);
		dataSource.setNumTestsPerEvictionRun(3);
		dataSource.setMinEvictableIdleTimeMillis(1800000L);
		return dataSource;
	}

}
