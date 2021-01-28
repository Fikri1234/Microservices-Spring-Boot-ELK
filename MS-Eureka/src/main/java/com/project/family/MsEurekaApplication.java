package com.project.family;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MsEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEurekaApplication.class, args);
	}

}
