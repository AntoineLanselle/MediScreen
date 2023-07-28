package com.mediscreen.NotesPatientService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.NotesPatientService.service.NotesService;
import com.mediscreen.NotesPatientService.dto.NotesDto;
import com.mediscreen.NotesPatientService.exception.NotesNotFoundException;

/**
 * Controller class for managing notes-related API endpoints. Handles requests
 * for retrieving, adding, updating, and deleting notes. Uses the NotesService
 * for handling the business logic. Maps incoming requests to the appropriate
 * methods and returns the corresponding responses.
 * 
 * @author Antoine Lanselle
 */
@RestController
public class NotesController {

	private Logger logger = LoggerFactory.getLogger(NotesController.class);

	@Autowired
	private NotesService notesService;

	/**
	 * Retrieves a notes by their ID.
	 *
	 * @param notesId the ID of the notes to retrieve.
	 * 
	 * @return a ResponseEntity with HTTP OK status and the retrieved notes as the
	 *         response body. If the notes is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@GetMapping("/patHistory/{notesId}")
	public ResponseEntity<NotesDto> getNotesById(@PathVariable("notesId") int notesId) {
		logger.info("GET request - getNotesById " + notesId);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(notesService.getNotesById(notesId));
		} catch (NotesNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Retrieves a list of notes based on the specified patient id.
	 *
	 * @param patId the patient id for filtering notes by patient id.
	 * 
	 * @return a ResponseEntity with HTTP OK status containing a list of NotesDto
	 *         objects matching the patient id.
	 */
	@GetMapping("/patHistory")
	public ResponseEntity<List<NotesDto>> getAllPatientNotes(@RequestParam int patId) {
		logger.info("GET request - getAllPatientNotes " + patId);

		return ResponseEntity.status(HttpStatus.OK).body(notesService.findByPatientId(patId));
	}

	/**
	 * Add a new notes using JSON data format.
	 *
	 * @param NotesDto a NotesDto object containing the notes's informations.
	 *
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the patient is added successfully, returns HTTP
	 *         CREATED status.
	 */
	@PostMapping(path = "/patHistory/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNotesJson(@RequestBody NotesDto notesDto) {
		logger.info("POST request - addNotes using Json");

		return addNotesInternal(notesDto);
	}

	/**
	 * Add a new notes using URL-encoded form data format.
	 *
	 * @param NotesDto a NotesDto object containing the notes's informations.
	 *
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the patient is added successfully, returns HTTP
	 *         CREATED status.
	 */
	@PostMapping(path = "/patHistory/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> addNotesUrl(@ModelAttribute NotesDto notesDto) {
		logger.info("POST request - addNotes using URL encoded");

		return addNotesInternal(notesDto);
	}

	/**
	 * Internal method to add a new notes to the system.
	 *
	 * @param notesDto a NotesDto object containing the notes's informations.
	 *
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the patient is added successfully, returns HTTP
	 *         CREATED status.
	 */
	private ResponseEntity<String> addNotesInternal(NotesDto notesDto) {
		notesService.createNotes(notesDto);
		return ResponseEntity.status(HttpStatus.CREATED).body("Notes has been added in the data base.");
	}

	/**
	 * Updates an existing notes in the database.
	 *
	 * @param notesId  the ID of the notes to update.
	 * @param notesDto a NotesDto object containing the updated notes's information.
	 * 
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the notes is updated successfully, returns HTTP
	 *         OK status. If the notes is not found, returns HTTP NOT_FOUND status.
	 */
	@PutMapping("/patHistory/update")
	public ResponseEntity<String> updateNotes(@RequestBody NotesDto notesDto) {
		logger.info("PUT request - updateNotes " + notesDto.getNotesId());

		try {
			notesService.updateNotes(notesDto);
		} catch (NotesNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notes with id: " + notesDto.getNotesId() + ", not found in data base !");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Notes has been updated in data base.");
	}

	/**
	 * Deletes a notes from the database.
	 *
	 * @param notesId the ID of the notes to delete.
	 * 
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the notes is deleted successfully, returns HTTP
	 *         OK status. If the notes is not found, returns HTTP NOT_FOUND status.
	 */
	@DeleteMapping("/patHistory/delete/{notesId}")
	public ResponseEntity<String> deleteNotes(@PathVariable("notesId") int notesId) {
		logger.info("DELETE request - deleteNotes " + notesId);

		try {
			notesService.deleteNotes(notesId);
		} catch (NotesNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Notes with id: " + notesId + ", not found in data base !");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Notes has been deleted from data base.");
	}

}
