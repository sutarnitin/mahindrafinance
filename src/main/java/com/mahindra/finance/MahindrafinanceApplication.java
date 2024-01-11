package com.mahindra.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan("com.mahindra.finance")
public class MahindrafinanceApplication {
	private static final Logger logger = LoggerFactory.getLogger(MahindrafinanceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MahindrafinanceApplication.class, args);
		logger.info("MAHINDRA FINANCE SERVICE");

	}

}
