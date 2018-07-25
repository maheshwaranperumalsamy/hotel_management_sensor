package com.hsms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@ComponentScan(basePackages = {"com.hsms"})
@EnableScheduling
public class HmsApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HmsApplication.class, args);
    }
}
