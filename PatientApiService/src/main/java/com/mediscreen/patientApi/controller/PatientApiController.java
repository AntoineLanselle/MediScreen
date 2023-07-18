package com.mediscreen.patientApi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;
import com.mediscreen.patientApi.service.PatientService;

import jakarta.validation.Valid;

/**
 * Controller class for managing patient-related API endpoints. Handles requests
 * for retrieving, adding, updating, and deleting patients. Uses the
 * PatientService for handling the business logic. Maps incoming requests to the
 * appropriate methods and returns the corresponding responses.
 * 
 * @author Antoine Lanselle
 */
@RestController
public class PatientApiController {

	private Logger logger = LoggerFactory.getLogger(PatientApiController.class);

	@Autowired
	public PatientService patientService;

	/**
	 * Retrieves all patients.
	 *
	 * @return a ResponseEntity with HTTP OK status and a list of patients as the
	 *         response body.
	 */
	@GetMapping("/patient")
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		logger.info("GET request - getAllPatients");

		return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatients());
	}
	
	/**
	 * Retrieves a list of patients based on the specified firstname and lastname.
	 * If both firstname and lastname are provided, it searches for patients matching both criteria.
	 *
	 * @param firstname the optional firstname parameter for filtering patients by firstname.
	 * @param lastname  the optional lastname parameter for filtering patients by lastname.
	 * 
	 * @return a ResponseEntity with HTTP OK status containing a list of PatientDto objects matching the search criteria.
	 */
	@GetMapping("/patient/search")
	public ResponseEntity<List<PatientDto>> searchPatients(@RequestParam(required = false) String firstname,
			@RequestParam(required = false) String lastname) {
		logger.info("GET request - searchPatients " + firstname + ", " + lastname);
		
		return ResponseEntity.status(HttpStatus.OK).body(patientService.searchPatients(firstname, lastname));
	}

	/**
	 * Retrieves a patient by their ID.
	 *
	 * @param patientId the ID of the patient to retrieve.
	 * 
	 * @return a ResponseEntity with HTTP OK status and the retrieved patient as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@GetMapping("/patient/{id}")
	public ResponseEntity<PatientDto> getPatient(@PathVariable("id") int patientId) {
		logger.info("GET request - getPatient " + patientId);

		try {
			PatientDto patient = patientService.findPatientById(patientId);
			return ResponseEntity.status(HttpStatus.OK).body(patient);
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	/**
	 * Adds a new patient to the database.
	 *
	 * @param patientDto a PatientDto object containing the patient's informations.
	 * @param result     the BindingResult object that holds the validation result
	 *                   of the request body.
	 * 
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the request body has validation errors, returns
	 *         HTTP NOT_ACCEPTABLE status. If the patient is added successfully,
	 *         returns HTTP CREATED status.
	 */
	@PostMapping("/patient/add")
	public ResponseEntity<String> addPatient(@Valid @RequestBody PatientDto patientDto, BindingResult result) {
		logger.info("POST request - addPatient " + patientDto.getFamily() + ", " + patientDto.getGiven());

		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(result.getAllErrors().toString());
		} else {
			patientService.addPatient(patientDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("Patient has been added in data base.");
		}
	}

	/**
	 * Updates an existing patient in the database.
	 *
	 * @param patientId  the ID of the patient to update.
	 * @param patientDto a PatientDto object containing the updated patient's
	 *                   information.
	 * @param result     the BindingResult object that holds the validation result
	 *                   of the request body.
	 * 
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the request body has validation errors, returns
	 *         HTTP NOT_ACCEPTABLE status. If the patient is updated successfully,
	 *         returns HTTP OK status. If the patient is not found, returns HTTP
	 *         NOT_FOUND status.
	 */
	@PutMapping("/patient/update/{id}")
	public ResponseEntity<String> updatePatient(@PathVariable("id") int patientId,
			@Valid @RequestBody PatientDto patientDto, BindingResult result) {
		logger.info("PUT request - updatePatient " + patientId);

		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(result.getAllErrors().toString());
		} else {
			try {
				patientService.updatePatient(patientId, patientDto);
			} catch (PatientNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Patient with id: " + patientId + ", not found in data base !");
			}
			return ResponseEntity.status(HttpStatus.OK).body("Patient has been updated in data base.");
		}
	}

	/**
	 * Deletes a patient from the database.
	 *
	 * @param patientId the ID of the patient to delete.
	 * 
	 * @return a ResponseEntity with HTTP status and a message indicating the result
	 *         of the operation. If the patient is deleted successfully, returns
	 *         HTTP OK status. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@DeleteMapping("/patient/delete/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable("id") int patientId) {
		logger.info("DELETE request - deletePatient " + patientId);

		try {
			patientService.deletePatient(patientId);
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Patient with id: " + patientId + ", not found in data base !");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Patient has been deleted from data base.");
	}

}
