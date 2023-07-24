package com.mediscreen.NotesPatientService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Notes Patient application. This class is responsible for
 * starting the Spring Boot application.
 * 
 * @author Antoine Lanselle
 */
@SpringBootApplication
public class NotesPatientServiceApplication {

	/**
	 * Main method to start the Notes Patient application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(NotesPatientServiceApplication.class, args);
	}

}
