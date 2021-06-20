package com.test.task.developer.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.test.task.developer.demo"})
public class DeveloperTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeveloperTestApplication.class, args);
    }

}
