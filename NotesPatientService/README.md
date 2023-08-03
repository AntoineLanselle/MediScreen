# NotesPatient

This application provides a RESTful API for managing patient's notes operations. 
It allows retrieving, adding, updating, and deleting notes records. 

## Note

In order to simplify the application's code and facilitate maintenance, 
slight modifications have been made to the queries provided, replacing the "¬" symbols with "&".

## Running the Application

To run the PatientApiService application, follow these steps:

1. Ensure you have MongoDb install on your pc.

2. Update the mongdb configuration in the application.properties file located in the src/main/resources directory.

4. - Running from the IDE : Open your integrated development environment and run the microservice 
     using the main class NotesPatientServiceApplication.
   - Running from .jar: If you prefer to run the microservice from the .jar file, use the following command
     in your terminal :
```
	java -jar NotesPatientService-0.0.1-SNAPSHOT.jar
```

5. The application will start running on http://localhost:8082.

## API Endpoints

The PatientApiService provides the following API endpoints:

- GET /patHistory/{notesId}: Retrieves a notes associated with a specified notes ID.
- GET /patHistory: Retrieves a list of notes associated with a specified patient ID. 
- POST /patHistory/add: Adds new notes using either JSON or URL-encoded data format.
- PUT /patHistory/update: Updates informations on an existing notes.
- DELETE /patHistory/delete/{notesId}: Deletes a note from the database according to its ID.

## Dependencies

The application has the following dependencies:

- Spring Boot
- MongoDb