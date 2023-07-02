package com.osi.service.registery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceRegistery1Application {

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegistery1Application.class, args);
	}

}
