package com.mediscreen.userInterface.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.mediscreen.userInterface.beans.NotesPatientBean;
import com.mediscreen.userInterface.proxies.NotesPatientServiceProxy;
import com.mediscreen.userInterface.proxies.PatientApiServiceProxy;
import com.mediscreen.userInterface.proxies.PatientAssessmentServiceProxy;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

	@Mock
	private PatientApiServiceProxy patientProxy;
	
	@Mock
	private NotesPatientServiceProxy notesProxy;
	
	@Mock
	private PatientAssessmentServiceProxy assessmentProxy;

	@InjectMocks
	private InterfaceController clientController;

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
		String testResult = clientController.patientListPage(model, null, null);

		// THEN
		assertEquals("PatientList", testResult);
		verify(model, times(1)).addAttribute("patients", patients);
	}
	
	@Test
	public void patientListPage_WithFirstnameOrLastnameNotNull_ShouldReturnViewPatientList() {
		// GIVEN
		List<PatientBean> patients = new ArrayList<>();
		patients.add(new PatientBean());
		patients.add(new PatientBean());
		when(patientProxy.searchPatients("Jean", null)).thenReturn(patients);

		// WHEN
		String testResult = clientController.patientListPage(model, "Jean", null);

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
    public void notesAddPage_ShouldReturnNotesAddView() {
        // GIVEN
        int patientId = 1;

        // WHEN
        String resultView = clientController.notesAddPage(model, patientId);

        // THEN
        assertEquals("NotesAdd", resultView);
        verify(model).addAttribute(eq("notesPatientBean"), any(NotesPatientBean.class));
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
    public void notesUpdatePage_ShouldReturnNotesUpdateView() {
        // GIVEN
        int notesId = 1;
        NotesPatientBean notesPatient = new NotesPatientBean();
        when(notesProxy.getNotesById(notesId)).thenReturn(notesPatient);

        // WHEN
        String resultView = clientController.notesUpdatePage(model, notesId);

        // THEN
        assertEquals("NotesUpdate", resultView);
        verify(model).addAttribute(eq("notesPatientBean"), eq(notesPatient));
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
		verify(patientProxy, times(1)).addPatientJson(patient);
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
		verify(patientProxy, never()).addPatientJson(patient);
	}
	
    @Test
    public void notesPatientAdd_ShouldRedirectToPatientNotesAddView() {
        // GIVEN
        NotesPatientBean notesPatientBean = new NotesPatientBean();
        notesPatientBean.setPatId(1);

        // WHEN
        String resultView = clientController.notesPatientAdd(notesPatientBean);

        // THEN
        assertEquals("redirect:/patient/1?notesAdd", resultView);
        verify(notesProxy).addNotesJson(notesPatientBean);
    }
	
	@Test
	public void patientUpdate_ShouldRedirectSuccess() {
		// GIVEN
		PatientBean patient = new PatientBean();
		patient.setGiven("Jean");
		patient.setFamily("Dumont");
		when(bindingResult.hasErrors()).thenReturn(false);

		// WHEN
		String testResult = clientController.patientUpdate(patient, bindingResult, 1, model);

		// THEN
		assertEquals("redirect:/patient/1?success", testResult);
		verify(patientProxy, times(1)).updatePatient(1, patient);
	}
	
	@Test
	public void patientUpdate_ShouldReturnViewPatientDetails() {
		// GIVEN
		PatientBean patient = new PatientBean();
		when(bindingResult.hasErrors()).thenReturn(true);

		// WHEN
		String testResult = clientController.patientUpdate(patient, bindingResult, 1, model);

		// THEN
		assertEquals("PatientDetails", testResult);
		verify(patientProxy, never()).updatePatient(1, patient);
	}
	
    @Test
    public void notesPatientUpdate_ShouldRedirectToPatientNotesUpdateView() {
        // GIVEN
        NotesPatientBean notesPatientBean = new NotesPatientBean();
        notesPatientBean.setNotesId(1);
        notesPatientBean.setPatId(1);

        // WHEN
        String resultView = clientController.notesPatientUpdate(notesPatientBean);

        // THEN
        assertEquals("redirect:/patient/1?notesUpt", resultView);
        verify(notesProxy).updateNotes(notesPatientBean);
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
	
    @Test
    public void notesPatientDelete_WhenDeleteIsSuccessful_ShouldRedirectToPatientNotesView() {
        // GIVEN
        int notesId = 1;
        int patientId = 1;

        // WHEN
        String resultView = clientController.notesPatientDelete(model, notesId, patientId);

        // THEN
        assertEquals("redirect:/patient/1?notesDel", resultView);
        verify(notesProxy).deleteNotes(notesId);
    }

    @Test
    public void notesPatientDelete_WhenDeleteFails_ShouldRedirectToPatientNotesErrorView() {
        // GIVEN
        int notesId = 1;
        int patientId = 1;
        doThrow(new RuntimeException()).when(notesProxy).deleteNotes(notesId);

        // WHEN
        String resultView = clientController.notesPatientDelete(model, notesId, patientId);

        // THEN
        assertEquals("redirect:/patient/1?notesError", resultView);
        verify(notesProxy).deleteNotes(notesId);
    }
    
    @Test
    public void testAssessmentById() {
        // GIVEN
        int patId = 1;
        String expectedAssessResult = "Some assessment result";
        when(assessmentProxy.assessmentByIdJson(patId)).thenReturn(expectedAssessResult);

        // WHEN
        String assessResult = clientController.assessmentById(patId, model);

        // THEN
        assertEquals(expectedAssessResult, assessResult);
        verify(assessmentProxy).assessmentByIdJson(patId);
        verify(model).addAttribute("assessResult", expectedAssessResult);
    }
	
}
