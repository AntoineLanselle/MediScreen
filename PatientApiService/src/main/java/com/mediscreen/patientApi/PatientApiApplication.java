package com.mediscreen.patientApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Patient API application. This class is responsible for
 * starting the Spring Boot application.
 * 
 * @author Antoine Lanselle
 */
@SpringBootApplication
public class PatientApiApplication {

	/**
	 * Main method to start the Patient API application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PatientApiApplication.class, args);
	}

}
