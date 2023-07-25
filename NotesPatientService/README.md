# NotesPatient

This application provides a RESTful API for managing patient's notes operations. 
It allows retrieving, adding, updating, and deleting notes records. 

## Running the Application

To run the PatientApiService application, follow these steps:

1. Ensure you have MongoDb install on your pc.

2. Update the mongdb configuration in the application.properties file located in the src/main/resources directory.

3. Build the application using Maven or your preferred IDE.

4. Run the application using the main class NotesServiceApplication.java.

5. The application will start running on http://localhost:8082.

## API Endpoints

The PatientApiService provides the following API endpoints:

- GET /patHistory: Retrieves a list of notes based on the specified patient id.
- GET /patHistory/{notesId}: Retrieves a notes by their ID.
- POST /patHistory/add: Add a new notes using JSON data format or URL-encoded form data.
- PUT /patHistory/update/{notesId}:  Updates an existing notes.
- DELETE /patHistory/delete/{notesId}: Deletes a notes.
## Dependencies

The application has the following dependencies:

- Spring Boot
- MongoDb
- Spring Web
- SLF4J for logging