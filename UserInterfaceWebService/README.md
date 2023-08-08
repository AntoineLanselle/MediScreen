# UserInterface

The User Interface Web Service is a part of the Mediscreen application. 
It provides a user interface for managing patients and their medical notes.

## Running the Application

To run the UserInterface application, follow these steps:

1. Start the PatientApiService on localhost:8081.

2. Start the NotesPatientService on localhost:8082.

3. - Running from the IDE : Open your integrated development environment and run the microservice 
     using the main class UserInterfaceWebServiceApplication.
   - Running from .jar: If you prefer to run the microservice from the .jar file, use the following command
     in your terminal :
```
java -jar UserInterfaceWebService-0.0.1-SNAPSHOT.jar
```

4.Access the application in your web browser at localhost:8080/patient.

## Dependencies

The application has the following dependencies:

- Spring Boot
- Spring Cloud OpenFeign
- Thymeleaf
