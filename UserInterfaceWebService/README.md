# UserInterface

This application is a User Interface (UI) for managing patient data. 
It allows users to perform CRUD operations on patient records. 
The UI interacts with the Patient API service to retrieve and manipulate the data.

## Running the Application

To run the UserInterface application, follow these steps:

1.Ensure that the required dependencies are installed and configured properly.

2.Start the Patient API service on localhost:8081.

3.Build and run the UserInterface application using your preferred IDE or by running the UserInterfaceWebServiceApplication class.

4.Access the application in your web browser at localhost:8080/patient.

## Dependencies

The application has the following dependencies:

- Spring Boot: Provides the framework for building the application.
- Spring Cloud OpenFeign: Enables easy integration with the Patient API service using declarative REST clients.
- Thymeleaf: Used as the template engine for rendering HTML pages.
- Jakarta Validation: Provides validation constraints for data fields in the PatientBean class.
- SLF4J for logging