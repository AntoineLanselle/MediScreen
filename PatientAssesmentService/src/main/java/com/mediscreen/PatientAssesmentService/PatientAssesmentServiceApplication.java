package com.mediscreen.PatientAssesmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class PatientAssesmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientAssesmentServiceApplication.class, args);
	}

}
