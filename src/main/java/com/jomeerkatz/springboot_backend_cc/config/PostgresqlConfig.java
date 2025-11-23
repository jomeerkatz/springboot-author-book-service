package com.jomeerkatz.springboot_backend_cc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class PostgresqlConfig {
    @Bean
    public JdbcTemplate jdbctemplate (final DataSource datasource) {
        return new JdbcTemplate(datasource);
    }
}
