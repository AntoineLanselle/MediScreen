package com.mediscreen.patientApi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mediscreen.patientApi.domain.Patient;
import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;
import com.mediscreen.patientApi.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {

	@Mock
	private PatientRepository patientRepository;
	
	@InjectMocks
	private PatientServiceImpl patientService;
	
	private Patient patient;

	@BeforeEach
	public void setUp() {
		patient = new Patient();
		patient.setFirstname("Jean");
		patient.setLastname("Dumont");
		patient.setDateOfBirth(LocalDate.now());
		patient.setGender("H");
	}
	
	@Test
	public void findAllPatients_shouldReturnListOfPatientDto() {
		// GIVEN
		List<Patient> listPatients = new ArrayList<>();
		when(patientRepository.findAll()).thenReturn(listPatients);
		
		// WHEN
		List<PatientDto> testResult = patientService.findAllPatients();
		
		// THEN
		assertEquals(listPatients, testResult);
	}
	
	  @Test
	    public void searchPatients_WithFirstnameAndLastname_ShouldReturnsMatchingPatient() {
	        // GIVEN
	        List<Patient> matchingPatients = new ArrayList<>();
	        Patient patient2 = new Patient();
	        patient2.setFirstname("Leo");
	        patient2.setLastname("Delatour");
	        matchingPatients.add(patient);
	        matchingPatients.add(patient2);
	        when(patientRepository.findByFirstnameContainingAndLastnameContaining("Jean", "Dumont"))
	                .thenReturn(matchingPatients);

	        // WHEN
	        List<PatientDto> result = patientService.searchPatients("Jean", "Dumont");

	        // THEN
	        assertEquals(matchingPatients.size(), result.size());
	    }

	    @Test
	    public void searchPatients_WithFirstnameOnly_ReturnsMatchingPatients() {
	        // GIVEN
	        List<Patient> matchingPatients = new ArrayList<>();
	        Patient patient2 = new Patient();
	        patient2.setFirstname("Leo");
	        patient2.setLastname("Delatour");
	        matchingPatients.add(patient);
	        matchingPatients.add(patient2);
	        when(patientRepository.findByFirstnameContaining("Jean")).thenReturn(matchingPatients);

	        // WHEN
	        List<PatientDto> result = patientService.searchPatients("Jean", null);

	        // THEN
	        assertEquals(matchingPatients.size(), result.size());
	    }

	    @Test
	    public void searchPatients_WithLastnameOnly_ReturnsMatchingPatients() {
	        // GIVEN
	        List<Patient> matchingPatients = new ArrayList<>();
	        Patient patient2 = new Patient();
	        patient2.setFirstname("Leo");
	        patient2.setLastname("Delatour");
	        matchingPatients.add(patient);
	        matchingPatients.add(patient2);
	        when(patientRepository.findByLastnameContaining("Delatour"))
	                .thenReturn(matchingPatients);

	        // WHEN
	        List<PatientDto> result = patientService.searchPatients(null, "Delatour");

	        // THEN
	        assertEquals(matchingPatients.size(), result.size());
	    }

	    @Test
	    public void testSearchPatients_WithoutParameters_ReturnsAllPatients() {
	        // GIVEN
	        List<Patient> allPatients = new ArrayList<>();
	        Patient patient2 = new Patient();
	        patient2.setFirstname("Leo");
	        patient2.setLastname("Delatour");
	        allPatients.add(patient);
	        allPatients.add(patient2);
	        when(patientRepository.findAll()).thenReturn(allPatients);

	        // WHEN
	        List<PatientDto> result = patientService.searchPatients(null, null);

	        // THEN
	        assertEquals(allPatients.size(), result.size());
	    }
	
	@Test
	public void findPatientById_shouldReturnSpecificPatientDto() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(patient);
		
		// WHEN
		PatientDto testResult = patientService.findPatientById(1);
		
		// THEN
		assertEquals(patient.getFirstname(), testResult.getGiven());
		assertEquals(patient.getLastname(), testResult.getFamily());
	}
	
	@Test
	public void findPatientById_shouldThrowPatientNotFundException() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(null);
		
		// WHEN // THEN
		assertThrows(PatientNotFoundException.class, () -> {
			patientService.findPatientById(1);
		});
	}
	
    @Test
    public void addPatient_ShouldSavedToRepository() {
        // GIVEN

        // WHEN
        patientService.addPatient(new PatientDto());

        // THEN
        verify(patientRepository, times(1)).save(any(Patient.class));
    }
    
	@Test
	public void updatePatient_shouldSavedToRepository() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(patient);
		
		// WHEN 
		patientService.updatePatient(1, new PatientDto());
		
		// THEN
		verify(patientRepository, times(1)).save(patient);
	}
    
	@Test
	public void updatePatient_shouldThrowPatientNotFundException() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(null);
		
		// WHEN // THEN
		assertThrows(PatientNotFoundException.class, () -> {
			patientService.updatePatient(1, new PatientDto());
		});
	}
	
	@Test
	public void deletePatient_shouldDeleteToRepository() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(patient);
		
		// WHEN 
		patientService.deletePatient(1);
		
		// THEN
		verify(patientRepository, times(1)).deleteById(1);;
	}
	
	@Test
	public void deletePatient_shouldThrowPatientNotFundException() {
		// GIVEN
		when(patientRepository.findById(1)).thenReturn(null);
		
		// WHEN // THEN
		assertThrows(PatientNotFoundException.class, () -> {
			patientService.deletePatient(1);
		});
	}
	
}
