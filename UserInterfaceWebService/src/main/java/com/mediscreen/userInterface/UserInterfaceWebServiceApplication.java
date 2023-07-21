package com.mediscreen.userInterface;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main class for the User Interface web service application. This class is
 * responsible for starting the Spring Boot application.
 * 
 * @author Antoine Lanselle
 */
@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class UserInterfaceWebServiceApplication {

	/**
	 * The main method that starts the User Interface web service application.
	 *
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(UserInterfaceWebServiceApplication.class, args);
	}

}
