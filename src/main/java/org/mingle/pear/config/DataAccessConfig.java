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
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
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
@ComponentScan(basePackages = "org.mingle.pear")
@ManagedResource(description = "DataSource Manager.")
public class DataAccessConfig {
	@Inject
	private PropertiesDatabase propDatabase;
	private int initialSize = 5;

	@ManagedAttribute(description = "The initialSize of connection pool.")
	public int getInitialSize() {
		return initialSize;
	}

	@ManagedOperation(description = "Change database connection pool initialSize.")
	@ManagedOperationParameters({
		@ManagedOperationParameter(name = "initialSize", description = "connection pool initialSize.")
	})
	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

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
		dataSource.setInitialSize(getInitialSize());
		dataSource.setTestOnBorrow(true);
		dataSource.setTestOnReturn(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setTimeBetweenEvictionRunsMillis(1800000L);
		dataSource.setNumTestsPerEvictionRun(3);
		dataSource.setMinEvictableIdleTimeMillis(1800000L);
		return dataSource;
	}

}
