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
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;
import com.mediscreen.patientApi.service.PatientService;

import jakarta.validation.Valid;

/**
 * Controller for demo.
 *
 * @author Antoine Lanselle
 */
@RestController
public class PatientApiController {

	private Logger logger = LoggerFactory.getLogger(PatientApiController.class);

	@Autowired
	public PatientService patientService;

	/**
	 * Returns all patients.
	 *
	 * @return a ResponseEntity with OK status and a list of Patients as body.
	 */
	@GetMapping("/patient")
	public ResponseEntity<List<PatientDto>> getAllPatients() {
		logger.info("GET request - getAllPatients");

		return ResponseEntity.status(HttpStatus.OK).body(patientService.findAllPatients());
	}

	/**
	 * Returns a patient.
	 *
	 * @return a ResponseEntity with OK status and as body a String message to
	 *         welcome user as body.
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
	 * Add the patient in data base with the given patient DTO.
	 * 
	 * @param patientDto a PatientDTO object containing patient informations.
	 *
	 * @return a ResponseEntity with OK status and as body a String message to
	 *         validate the operation.
	 * @return
	 */
	@PostMapping("/patient/add")
	public ResponseEntity<String> addPatient(@Valid @RequestBody PatientDto patientDto, BindingResult result) {
		logger.info("POST request - addPatient " + patientDto.getFamily() + ", " + patientDto.getGiven());
		
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(result.getAllErrors().toString());
		} else {
			patientService.addPatient(patientDto);
			return ResponseEntity.status(HttpStatus.CREATED).body("Patient has been added in data base.");
		}
	}

	/**
	 * .
	 * 
	 * @param
	 * @param patientDto a PatientDTO object containing new patient informations.
	 *
	 * @return a ResponseEntity with OK status and as body a String message to
	 *         validate the operation.
	 */
	@PutMapping("/patient/update/{id}")
	public ResponseEntity<String> updatePatient(@PathVariable("id") int patientId,
			@Valid @RequestBody PatientDto patientDto, BindingResult result) {
		logger.info("PUT request - updatePatient " + patientId);

		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
					.body(result.getAllErrors().toString());
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
	 * .
	 * 
	 * @param patientDto a PatientDTO object containing patient informations.
	 *
	 * @return a ResponseEntity with OK status and as body a String message to
	 *         validate the operation.
	 * @return
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
