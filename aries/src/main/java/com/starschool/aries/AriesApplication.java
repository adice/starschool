package com.starschool.aries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AriesApplication {

    public static void main(String[] args) {
        SpringApplication.run(AriesApplication.class, args);
    }

}
