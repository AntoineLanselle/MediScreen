# PatientApi

This application provides a RESTful API for managing patient-related operations. 
It allows retrieving, adding, updating, and deleting patient records. 

## Running the Application

To run the PatientApiService application, follow these steps:

1. Ensure you have a MySQL database set up and running.

2. Update the database configuration in the application.properties file located in the src/main/resources directory
   with your MySQL database credentials.

3. Run this command in your SGBD to run the sql script:
```
source path/to/application/MediScreen/PatientDatabase/data_base_structure.sql
```
4. - Running from the IDE : Open your integrated development environment and run the microservice 
     using the main class PatientApiApplication.
   - Running from .jar: If you prefer to run the microservice from the .jar file, use the following command
     in your terminal :
```
java -jar PatientApiService-0.0.1-SNAPSHOT.jar
```

5. The application will start running on http://localhost:8081.

## API Endpoints

The PatientApiService provides the following API endpoints:

- GET /patient: Retrieves all registered patients.
- GET /patient/search: Retrieves a list of patients based on the specified firstname and/or lastname. 
  If both parameters (firstname and lastname) are provided, 
  the search is performed by filtering patients matching these criteria.
- GET /patient/{id}: Retrieves a patient by their ID.
- POST /patient/add: Adds a new patient to the database. 
  You can use JSON or URL-encoded format to send patient information.
- PUT /patient/update/{id}: Updates an existing patient's information by patient ID.
- DELETE /patient/delete/{id}: Deletes a patient from the database by ID.

## Dependencies

The application has the following dependencies:

- Spring Boot
- Spring Data JPA
- MySQL Connector