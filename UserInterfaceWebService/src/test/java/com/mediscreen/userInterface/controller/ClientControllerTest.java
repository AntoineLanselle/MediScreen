package com.mediscreen.userInterface.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.mediscreen.userInterface.beans.PatientBean;
import com.mediscreen.userInterface.proxies.PatientApiServiceProxy;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

	@Mock
	private PatientApiServiceProxy patientProxy;

	@InjectMocks
	private ClientController clientController;

	@Mock
	Model model;
	
	@Mock
	BindingResult bindingResult;

	@Test
	public void patientListPage_ShouldReturnViewPatientList() {
		// GIVEN
		List<PatientBean> patients = new ArrayList<>();
		patients.add(new PatientBean());
		patients.add(new PatientBean());
		when(patientProxy.getAllPatients()).thenReturn(patients);

		// WHEN
		String testResult = clientController.patientListPage(model);

		// THEN
		assertEquals("PatientList", testResult);
		verify(model, times(1)).addAttribute("patients", patients);
	}

	@Test
	public void patientAddPage_ShouldReturnViewPatientAdd() {
		// GIVEN

		// WHEN
		String testResult = clientController.patientAddPage(model);

		// THEN
		assertEquals("PatientAdd", testResult);
	}

	@Test
	public void patientDetailsPage_ShouldReturnViewPatientDetails() {
		// GIVEN
		PatientBean patient = new PatientBean();
		when(patientProxy.getPatient(1)).thenReturn(patient);

		// WHEN
		String testResult = clientController.patientDetailsPage(model, 1);

		// THEN
		assertEquals("PatientDetails", testResult);
		verify(model, times(1)).addAttribute("patientBean", patient);
	}
	
	@Test
	public void patientAdd_ShouldRedirect() {
		// GIVEN
		PatientBean patient = new PatientBean();
		patient.setGiven("Jean");
		patient.setFamily("Dumont");
		when(bindingResult.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = clientController.patientAdd(patient, bindingResult);

		// THEN
		assertEquals("redirect:/patient?addSuccess", testResult);
		verify(patientProxy, times(1)).addPatient(patient);
	}
	
	@Test
	public void patientAdd_ShouldReturnViewPatientAdd() {
		// GIVEN
		PatientBean patient = new PatientBean();
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = clientController.patientAdd(patient, bindingResult);

		// THEN
		assertEquals("PatientAdd", testResult);
		verify(patientProxy, never()).addPatient(patient);
	}
	
	@Test
	public void patientUpdate_ShouldRedirectSuccess() {
		// GIVEN
		PatientBean patient = new PatientBean();
		patient.setGiven("Jean");
		patient.setFamily("Dumont");
		when(bindingResult.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = clientController.patientUpdate(patient, bindingResult, 1);

		// THEN
		assertEquals("redirect:/patient/1?success", testResult);
		verify(patientProxy, times(1)).updatePatient(1, patient);
	}
	
	@Test
	public void patientUpdate_ShouldRedirect() {
		// GIVEN
		PatientBean patient = new PatientBean();
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = clientController.patientUpdate(patient, bindingResult, 1);

		// THEN
		assertEquals("redirect:/patient/1", testResult);
		verify(patientProxy, never()).updatePatient(1, patient);
	}

	@Test
	public void patientDelete_ShouldRedirectSuccess() {
		// GIVEN

		// WHEN
		String testResult = clientController.patientDelete(model, 1);

		// THEN
		assertEquals("redirect:/patient?delSuccess", testResult);
		verify(patientProxy, times(1)).deletePatient(1);
	}
	
	@Test
	public void patientDelete_ShouldRedirectError() {
		// GIVEN
		 doThrow(RuntimeException.class).when(patientProxy).deletePatient(1);

		// WHEN
		String testResult = clientController.patientDelete(model, 1);

		// THEN
		assertEquals("redirect:/patient?delError", testResult);
		verify(patientProxy, times(1)).deletePatient(1);
	}
	
}
