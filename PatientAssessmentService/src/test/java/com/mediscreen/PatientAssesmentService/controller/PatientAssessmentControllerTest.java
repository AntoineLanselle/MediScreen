package com.mediscreen.PatientAssesmentService.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.service.PatientAssessmentService;

public class PatientAssessmentControllerTest {

    @Mock
    private PatientAssessmentService assessmentService;

    @InjectMocks
    private PatientAssessmentController assessmentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void assessmentByIdJson_WhenPatientFound_ShouldReturnOk() {
        // GIVEN
        int patId = 1;
        PatientBean patient = new PatientBean();
        when(assessmentService.getPatientById(patId)).thenReturn(patient);
        when(assessmentService.getAssessmentResult(patient)).thenReturn("Assessment result");

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByIdJson(patId);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assessment result", response.getBody());
    }

    @Test
    void assessmentByIdJson_WhenPatientNotFound_ShouldReturnNotFound() {
        // GIVEN
        int patId = 1;
        when(assessmentService.getPatientById(patId)).thenThrow(new PatientNotFoundException("Patient not found"));

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByIdJson(patId);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void assessmentByNameJson_WhenPatientFound_ShouldReturnOk() {
        // GIVEN
        String familyName = "Doe";
        String given = "John";
        PatientBean patient = new PatientBean();
        when(assessmentService.getPatientByFamilyNameAndGiven(familyName, given)).thenReturn(patient);
        when(assessmentService.getAssessmentResult(patient)).thenReturn("Assessment result");

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByNameJson(familyName, given);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assessment result", response.getBody());
    }

    @Test
    void assessmentByNameJson_WhenPatientNotFound_ShouldReturnNotFound() {
        // GIVEN
        String familyName = "Doe";
        String given = "John";
        when(assessmentService.getPatientByFamilyNameAndGiven(familyName, given))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByNameJson(familyName, given);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void assessmentByIdUrl_WhenPatientFound_ShouldReturnOk() {
        // GIVEN
        int patId = 1;
        PatientBean patient = new PatientBean();
        when(assessmentService.getPatientById(patId)).thenReturn(patient);
        when(assessmentService.getAssessmentResult(patient)).thenReturn("Assessment result");

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByIdUrl(patId);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assessment result", response.getBody());
    }

    @Test
    void assessmentByIdUrl_WhenPatientNotFound_ShouldReturnNotFound() {
        // GIVEN
        int patId = 1;
        when(assessmentService.getPatientById(patId)).thenThrow(new PatientNotFoundException("Patient not found"));

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByIdUrl(patId);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void assessmentByNameUrl_WhenPatientFound_ShouldReturnOk() {
        // GIVEN
        String familyName = "Doe";
        String given = "John";
        PatientBean patient = new PatientBean();
        when(assessmentService.getPatientByFamilyNameAndGiven(familyName, given)).thenReturn(patient);
        when(assessmentService.getAssessmentResult(patient)).thenReturn("Assessment result");

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByNameUrl(familyName, given);

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assessment result", response.getBody());
    }

    @Test
    void assessmentByNameUrl_WhenPatientNotFound_ShouldReturnNotFound() {
        // GIVEN
        String familyName = "Doe";
        String given = "John";
        when(assessmentService.getPatientByFamilyNameAndGiven(familyName, given))
                .thenThrow(new PatientNotFoundException("Patient not found"));

        // WHEN
        ResponseEntity<String> response = assessmentController.assessmentByNameUrl(familyName, given);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}
