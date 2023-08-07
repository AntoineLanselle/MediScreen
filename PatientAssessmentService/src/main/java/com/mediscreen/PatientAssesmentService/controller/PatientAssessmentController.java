package com.mediscreen.PatientAssesmentService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.service.PatientAssessmentService;

/**
 * This class represents the controller for handling patient assessment
 * requests. Uses the PatientAssessmentService for handling the logic. The
 * assessment results are returned as a ResponseEntity containing a String
 * response.
 * 
 * @author Antoine Lanselle
 */
@RestController
public class PatientAssessmentController {

	private Logger logger = LoggerFactory.getLogger(PatientAssessmentController.class);

	@Autowired
	private PatientAssessmentService assessmentService;

	/**
	 * Assess a patient by their ID using Json data format.
	 *
	 * @param id The ID of the patient to be assessed.
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@PostMapping(path = "/assess/id", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assessmentByIdJson(@RequestParam int patId) {
		logger.info("POST request - assessmentById " + patId + " using Json");

		return assessmentById(patId);
	}

	/**
	 * Assess a patient by their ID using URL-encoded form data format.
	 *
	 * @param id The ID of the patient to be assessed.
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@PostMapping(path = "/assess/id", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> assessmentByIdUrl(@RequestParam int patId) {
		logger.info("POST request - assessmentById " + patId + " using URL encoded");

		return assessmentById(patId);
	}

	/**
	 * Assess a patient by their ID.
	 *
	 * @param id The ID of the patient to be assessed.
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	private ResponseEntity<String> assessmentById(int patId) {
		try {
			PatientBean patient = assessmentService.getPatientById(patId);
			return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAssessmentResult(patient));
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	/**
	 * Assess a patient by their family name and optionally their given name using
	 * Json data format.
	 *
	 * @param familyName The family name of the patient to be assessed.
	 * @param given      The given name of the patient (optional).
	 * 
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@PostMapping(path = "/assess/familyName", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assessmentByNameJson(@RequestParam(required = true) String familyName,
			@RequestParam(required = false) String given) {
		logger.info("POST request - assessmentByName " + familyName + " " + given + " using Json");

		return assessmentByName(familyName, given);
	}

	/**
	 * Assess a patient by their family name and optionally their given name using
	 * URL-encoded form data format.
	 *
	 * @param familyName The family name of the patient to be assessed.
	 * @param given      The given name of the patient (optional).
	 * 
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	@PostMapping(path = "/assess/familyName", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> assessmentByNameUrl(@RequestParam(required = true) String familyName,
			@RequestParam(required = false) String given) {
		logger.info("POST request - assessmentByName " + familyName + " " + given + " using URL encoded");

		return assessmentByName(familyName, given);
	}

	/**
	 * Assess a patient by their family name and optionally their given name.
	 *
	 * @param familyName The family name of the patient to be assessed.
	 * @param given      The given name of the patient (optional).
	 * 
	 * @return a ResponseEntity with HTTP OK status and the assess result as the
	 *         response body. If the patient is not found, returns HTTP NOT_FOUND
	 *         status.
	 */
	private ResponseEntity<String> assessmentByName(String familyName, String given) {
		try {
			PatientBean patient = assessmentService.getPatientByFamilyNameAndGiven(familyName, given);
			return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAssessmentResult(patient));
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}
