package com.insurance.PolicyManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PolicyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolicyManagementApplication.class, args);
	}

}
