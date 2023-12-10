package com.g2it.realestate.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:bootstrap.yml")
@ConfigurationProperties("spring.datasource.hikari")
@Data
public class HikariProperties {

	private String poolName;
	private int minimumIdle;
	private int maximumPoolSize;
	private int idleTimeout;
}
