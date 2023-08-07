package com.mediscreen.PatientAssesmentService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.beans.NotesPatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.proxies.NotesPatientServiceProxy;
import com.mediscreen.PatientAssesmentService.proxies.PatientApiServiceProxy;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class PatientAssessmentServiceImplTest {

	@Mock
	private PatientApiServiceProxy patientProxy;

	@Mock
	private NotesPatientServiceProxy notesProxy;

	private PatientAssessmentService patientAssessmentService;

	@BeforeEach
	void setUp() {
		patientAssessmentService = new PatientAssessmentServiceImpl(patientProxy, notesProxy);
	}

	@Test
	void testGetPatientById_ExistingPatient_ShouldReturnPatient() {
		// GIVEN
		int patientId = 1;
		PatientBean expectedPatient = new PatientBean();
		expectedPatient.setId(patientId);
		when(patientProxy.getPatient(patientId)).thenReturn(expectedPatient);

		// WHEN
		PatientBean result = patientAssessmentService.getPatientById(patientId);

		// THEN
		assertEquals(expectedPatient, result);
	}

	@Test
	void testGetPatientById_NonExistingPatient_ShouldThrowException() {
		// GIVEN
		int patientId = 1;
		when(patientProxy.getPatient(patientId)).thenThrow(FeignException.NotFound.class);

		// WHEN + THEN
		assertThrows(PatientNotFoundException.class, () -> patientAssessmentService.getPatientById(patientId));
	}

	@Test
	void testGetPatientByFamilyNameAndGiven_ExistingPatient_ShouldReturnPatient() {
		// GIVEN
		String familyName = "Smith";
		String givenName = "John";
		List<PatientBean> expectedPatients = new ArrayList<>();
		PatientBean expectedPatient = new PatientBean();
		expectedPatient.setId(1);
		expectedPatients.add(expectedPatient);
		when(patientProxy.searchPatients(givenName, familyName)).thenReturn(expectedPatients);

		// WHEN
		PatientBean result = patientAssessmentService.getPatientByFamilyNameAndGiven(familyName, givenName);

		// THEN
		assertEquals(expectedPatient, result);
	}

	@Test
	void testGetPatientByFamilyNameAndGiven_MultiplePatients_ShouldThrowException() {
		// GIVEN
		String familyName = "Smith";
		String givenName = "John";
		List<PatientBean> expectedPatients = new ArrayList<>();
		expectedPatients.add(new PatientBean());
		expectedPatients.add(new PatientBean());
		when(patientProxy.searchPatients(givenName, familyName)).thenReturn(expectedPatients);

		// WHEN + THEN
		assertThrows(PatientNotFoundException.class,
				() -> patientAssessmentService.getPatientByFamilyNameAndGiven(familyName, givenName));
	}

	@Test
	void testGetPatientByFamilyNameAndGiven_NonExistingPatient_ShouldThrowException() {
		// GIVEN
		String familyName = "Smith";
		String givenName = "John";
		when(patientProxy.searchPatients(givenName, familyName)).thenReturn(new ArrayList<>());

		// WHEN + THEN
		assertThrows(PatientNotFoundException.class,
				() -> patientAssessmentService.getPatientByFamilyNameAndGiven(familyName, givenName));
	}

	@Test
	void testGetAssessmentResult() {
		// GIVEN
		PatientBean patient = new PatientBean();
		patient.setId(1);
		patient.setFamily("Smith");
		patient.setGiven("John");
		patient.setDob(LocalDate.of(1985, 8, 7));
		patient.setSex("M");

		List<NotesPatientBean> notesList = new ArrayList<>();
		NotesPatientBean notes1 = new NotesPatientBean();
		notes1.setE("Cholesterol");
		NotesPatientBean notes2 = new NotesPatientBean();
		notes2.setE("Weight");
		notesList.add(notes1);
		notesList.add(notes2);
		when(notesProxy.getAllPatientNotes(anyInt())).thenReturn(notesList);

		// WHEN
		String result = patientAssessmentService.getAssessmentResult(patient);

		// THEN
		assertEquals("Patient: John Smith (age 38) diabetes assessment is: Borderline", result);
	}

}
