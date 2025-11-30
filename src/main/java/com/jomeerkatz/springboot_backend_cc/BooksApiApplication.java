package com.jomeerkatz.springboot_backend_cc;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class BooksApiApplication implements CommandLineRunner {

    private final DataSource datasource;

    public BooksApiApplication(final DataSource datasource) {this.datasource = datasource;}

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}

    public void run(final String ...args) {
        log.info("datasource: "+datasource.toString());
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
        jdbcTemplate.execute("select 1");
    }
}
