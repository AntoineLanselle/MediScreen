package com.mediscreen.PatientAssesmentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the Patient Assessment application. This class is responsible
 * for starting the Spring Boot application.
 * 
 * @author Antoine Lanselle
 */
@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class PatientAssessmentServiceApplication {

	/**
	 * Main method to start the Patient Assessment application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PatientAssessmentServiceApplication.class, args);
	}

}
