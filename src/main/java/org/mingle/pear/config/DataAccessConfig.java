package org.mingle.pear.config;

import java.util.Collections;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.mingle.pear.properties.PropertiesDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(emf);
		return txManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPersistenceUnitName("persistenceUnit");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaPropertyMap(Collections.singletonMap("hibernate.session_factory_name", "mySessionFactory"));
		return emf;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource(propDatabase.getUrl(), propDatabase.getUsername(), propDatabase.getPassword());
		dataSource.setDriverClassName(propDatabase.getDriverClassName());
		return dataSource;
	}

}
