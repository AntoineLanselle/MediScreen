package com.mediscreen.patientApi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;
import com.mediscreen.patientApi.service.PatientService;

@ExtendWith(MockitoExtension.class)
public class PatientApiControllerTest {

	@Mock
	private PatientService patientService;

	@InjectMocks
	private PatientApiController patientController;

	@Mock
	private BindingResult bindingResult;

	private PatientDto patientDto;

	@BeforeEach
	public void setUp() {
		patientDto = new PatientDto();
		patientDto.setGiven("Jean");
		patientDto.setFamily("Dumont");
		patientDto.setDob("1950-02-15");
		patientDto.setSex("H");
	}

	@Test
	public void getAllPatients_shouldReturnResponseEntityWithOkStatusAndListPatientDtoAsBody() {
		// GIVEN
		List<PatientDto> listPatients = new ArrayList<>();
		when(patientService.findAllPatients()).thenReturn(listPatients);

		// WHEN
		ResponseEntity<List<PatientDto>> testResult = patientController.getAllPatients();

		// THEN
		assertEquals(listPatients, testResult.getBody());
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
	}

	@Test
	public void searchPatients_shouldReturnResponseEntityWithOkStatusAndListPatientDtoAsBody() {
		// GIVEN
		List<PatientDto> expectedPatients = new ArrayList<>();
		expectedPatients.add(patientDto);
		when(patientService.searchPatients("Jean", "Dumont")).thenReturn(expectedPatients);

		// WHEN
		ResponseEntity<List<PatientDto>> response = patientController.searchPatients("Jean", "Dumont");

		// THEN
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedPatients, response.getBody());
	}

	@Test
	public void getPatient_shouldReturnResponseEntityWithOkStatusAndSpecificPatientDtoAsBody() {
		// GIVEN
		PatientDto patientDto = new PatientDto();
		when(patientService.findPatientById(1)).thenReturn(patientDto);

		// WHEN
		ResponseEntity<PatientDto> testResult = patientController.getPatient(1);

		// THEN
		assertEquals(patientDto, testResult.getBody());
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
	}

	@Test
	public void getPatient_shouldReturnResponseEntityWithNotFoundStatus() {
		// GIVEN
		doThrow(PatientNotFoundException.class).when(patientService).findPatientById(1);

		// WHEN
		ResponseEntity<PatientDto> testResult = patientController.getPatient(1);

		// THEN
		assertEquals(HttpStatus.NOT_FOUND, testResult.getStatusCode());
	}

	@Test
	public void addPatient_shouldReturnResponseEntityWithCreatedStatusAndStringAsBody() {
		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(false);
		
		// WHEN 
		ResponseEntity<String> testResult = patientController.addPatient(patientDto, bindingResult);
		
		// THEN
		assertEquals("Patient has been added in data base.", testResult.getBody());
		assertEquals(HttpStatus.CREATED, testResult.getStatusCode());
	}

	@Test
	public void addPatient_shouldReturnResponseEntityWithNotAcceptableStatusAndStringAsBody() {
		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);
		
		// WHEN 
		ResponseEntity<String> testResult = patientController.addPatient(patientDto, bindingResult);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, testResult.getStatusCode());
	}

	@Test
	public void updatePatient_shouldReturnResponseEntityWithOkStatusAndStringAsBody() {
		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(false);
		
		// WHEN 
		ResponseEntity<String> testResult = patientController.updatePatient(1, patientDto, bindingResult);
		
		// THEN
		assertEquals("Patient has been updated in data base.", testResult.getBody());
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
	}

	@Test
	public void updatePatient_shouldReturnResponseEntityWithNotFoundStatusAndStringAsBody() {
		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(PatientNotFoundException.class).when(patientService).updatePatient(1,patientDto);
		
		// WHEN 
		ResponseEntity<String> testResult = patientController.updatePatient(1, patientDto, bindingResult);
		
		// THEN
		assertEquals("Patient with id: 1, not found in data base !", testResult.getBody());
		assertEquals(HttpStatus.NOT_FOUND, testResult.getStatusCode());
	}

	@Test
	public void updatePatient_shouldReturnResponseEntityWithNotAcceptableStatusAndStringAsBody() {
		// GIVEN
		when(bindingResult.hasErrors()).thenReturn(true);
		
		// WHEN 
		ResponseEntity<String> testResult = patientController.updatePatient(1, patientDto, bindingResult);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, testResult.getStatusCode());
	}

	@Test
	public void deletePatient_shouldReturnResponseEntityWithOkStatusAndStringAsBody() {
		// GIVEN

		// WHEN
		ResponseEntity<String> testResult = patientController.deletePatient(1);

		// THEN
		assertEquals("Patient has been deleted from data base.", testResult.getBody());
		assertEquals(HttpStatus.OK, testResult.getStatusCode());
	}

	@Test
	public void deletePatient_shouldReturnResponseEntityWithNotFoundStatusAndStringAsBody() {
		// GIVEN
		doThrow(PatientNotFoundException.class).when(patientService).deletePatient(1);

		// WHEN
		ResponseEntity<String> testResult = patientController.deletePatient(1);

		// THEN
		assertEquals("Patient with id: 1, not found in data base !", testResult.getBody());
		assertEquals(HttpStatus.NOT_FOUND, testResult.getStatusCode());
	}

}
