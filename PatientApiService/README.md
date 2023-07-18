# PatientApi

This application provides a RESTful API for managing patient-related operations. 
It allows retrieving, adding, updating, and deleting patient records. 

## Running the Application

To run the PatientApiService application, follow these steps:

1. Ensure you have a MySQL database set up and running.
2. Update the database configuration in the application.properties file located in the src/main/resources directory with your MySQL database credentials.
3. Build the application using Maven or your preferred build tool.
4. Run the application using the main class PatientApiApplication.java.
5. The application will start running on http://localhost:8081.

## API Endpoints

The PatientApiService provides the following API endpoints:

- GET /patient: Retrieves all patients.
- GET /patient/{id}: Retrieves a patient by their ID.
- POST /patient/add: Adds a new patient.
- PUT /patient/update/{id}: Updates an existing patient.
- DELETE /patient/delete/{id}: Deletes a patient.

## Dependencies

The application has the following dependencies:

- Spring Boot
- Spring Data JPA
- Spring Web
- MySQL Connector
- SLF4J for logging