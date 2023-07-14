package com.mediscreen.userInterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class UserInterfaceWebServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(UserInterfaceWebServiceApplication.class, args);
	}

}

