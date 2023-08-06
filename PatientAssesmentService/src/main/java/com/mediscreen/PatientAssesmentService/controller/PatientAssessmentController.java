package com.mediscreen.PatientAssesmentService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.service.PatientAssessmentService;

@RestController
public class PatientAssessmentController {

	//private Logger logger = LoggerFactory.getLogger(PatientAssessmentController.class);

	@Autowired
	private PatientAssessmentService assessmentService;

	@PostMapping("/assess/id")
	public ResponseEntity<String> assessmentById(@RequestParam(required = true) int id) {
		// TODO logger
		try {
			PatientBean patient = assessmentService.getPatientById(id);
			return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAssessmentResult(patient));
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping("/assess/familyName")
	public ResponseEntity<String> assessmentByName(@RequestParam(required = true) String familyName,
			@RequestParam(required = false) String given) {
		// TODO logger
		try {
			PatientBean patient = assessmentService.getPatientByFamilyNameAndGiven(familyName, given);
			return ResponseEntity.status(HttpStatus.OK).body(assessmentService.getAssessmentResult(patient));
		} catch (PatientNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}
