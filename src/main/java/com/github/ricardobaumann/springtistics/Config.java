package com.github.ricardobaumann.springtistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {

    @Bean
    List<Transaction> transactions() {
        return new ArrayList<>();
    }

}
