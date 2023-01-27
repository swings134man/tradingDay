package com.trading.day;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@EnableJpaAuditing
@EnableBatchProcessing // batch
@EnableScheduling 	   // Scheduling
public class DayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayApplication.class, args);
	}

}
