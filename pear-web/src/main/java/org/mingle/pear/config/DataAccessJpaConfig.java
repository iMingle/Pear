/**
 * Copyright (c) 2016, Mingle. All rights reserved.
 */
package org.mingle.pear.config;

import org.mingle.pear.properties.PropertiesDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;

/**
 * 数据访问配置
 *
 * @author Mingle
 * @since 1.8
 */
@Configuration
@EnableTransactionManagement
@ManagedResource(description = "DataSource Manager.")
public class DataAccessJpaConfig extends DataAccessConfig {
    @Inject private PropertiesDatabase propDatabase;

    @Bean
    public PlatformTransactionManager transactionManagerJpa(EntityManagerFactory entityManagerFactory) {
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

    @Override
    public PropertiesDatabase getPropDatabase() {
        return propDatabase;
    }
}
