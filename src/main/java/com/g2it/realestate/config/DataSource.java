package com.g2it.realestate.config;

import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties("spring.datasource")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager", basePackages = {
		"com.g2it.realestate.repository" })
public class DataSource extends HikariPoolConfig {
	
	@Value("${spring.model.package}") 
	private String MODEL_PACKAGE;
	
	public DataSource(HikariProperties hikariProperties) {
		super(hikariProperties);
	}

	@Bean
	public HikariDataSource dataSourceCustomer() {
		return new HikariDataSource(this);
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			final HikariDataSource dataSourceCustomer) {

		return new LocalContainerEntityManagerFactoryBean() {
			{
				setDataSource(dataSourceCustomer);
				setPersistenceProviderClass(HibernatePersistenceProvider.class);
				setPersistenceUnitName("realestate");
				setPackagesToScan(MODEL_PACKAGE);
				setJpaProperties(JPA_WRITE_PROPERTIES);
			}
		};
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
