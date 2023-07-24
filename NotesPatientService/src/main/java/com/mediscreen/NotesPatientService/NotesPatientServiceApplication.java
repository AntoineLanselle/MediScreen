package com.mediscreen.NotesPatientService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class NotesPatientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesPatientServiceApplication.class, args);
	}

}
