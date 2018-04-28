package com.github.ricardobaumann.springtistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringtisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringtisticsApplication.class, args);
	}
}
