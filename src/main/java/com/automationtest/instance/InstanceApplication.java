package com.automationtest.instance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class InstanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstanceApplication.class, args);
    }

}
