# PatientAssessment

This application is responsible for providing diabetes risk assessment for patients based on their medical notes
and personal information. It communicates with two other services, the NotesPatientService and the PatientApiService.

## Running the Application

To run the PatientAssessment application, follow these steps:

1. Start the PatientApiService on localhost:8081.

2. Start the NotesPatientService on localhost:8082.

3. - Running from the IDE : Open your integrated development environment and run the microservice 
     using the main class PatientAssessmentApplication.
   - Running from .jar: If you prefer to run the microservice from the .jar file, use the following command
     in your terminal :
```
java -jar PatientAssessmentService-0.0.1-SNAPSHOT.jar
```

4. The application will start running on http://localhost:8083.

## Endpoints

The PatientAssessmentService provides the following endpoints:

- POST /assess/id: Assess a patient by their ID.
- POST /assess/familyName: Assess a patient by their family name and optionally their given name.

## Dependencies

The application has the following dependencies:

- Spring Boot
- Spring Cloud OpenFeign