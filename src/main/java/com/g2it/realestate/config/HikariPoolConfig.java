package com.g2it.realestate.config;

import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;

public class HikariPoolConfig extends HikariConfig {

    protected HikariProperties hikariCRMProperties;

    @SuppressWarnings("serial")
	protected final Properties JPA_WRITE_PROPERTIES = new Properties() {{
        put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        put("hibernate.hbm2ddl.auto", "update");
        put("hibernate.ddl-auto", "update");
        put("show-sql", "true");
    }};

    protected HikariPoolConfig(HikariProperties hikariCRMProperties) {
        this.hikariCRMProperties = hikariCRMProperties;
        setPoolName(this.hikariCRMProperties.getPoolName());
        setMinimumIdle(this.hikariCRMProperties.getMinimumIdle());
        setMaximumPoolSize(this.hikariCRMProperties.getMaximumPoolSize());
        setIdleTimeout(this.hikariCRMProperties.getIdleTimeout());
		this.hikariCRMProperties = new HikariProperties();
    }
}
