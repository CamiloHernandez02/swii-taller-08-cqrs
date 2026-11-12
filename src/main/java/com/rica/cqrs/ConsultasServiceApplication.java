package com.rica.cqrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsultasServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsultasServiceApplication.class, args);
    }
}
