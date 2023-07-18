package com.mediscreen.patientApi.service;

import java.util.List;

import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;

/**
 * Service interface for managing patients.
 * 
 * @author Antoine Lanselle
 */
public interface PatientService {

	public List<PatientDto> findAllPatients();

	public PatientDto findPatientById(int patientId) throws PatientNotFoundException;

	public void addPatient(PatientDto patientDto);

	public void updatePatient(int patientId, PatientDto newPatientInfos) throws PatientNotFoundException;

	public void deletePatient(int patientId) throws PatientNotFoundException;

}
