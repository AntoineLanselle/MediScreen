package com.mediscreen.PatientAssesmentService.service;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;

/**
 * Service interface for patient assessment.
 * 
 * @author Antoine Lanselle
 */
public interface PatientAssessmentService {

	public PatientBean getPatientById(int id);
	
	public PatientBean getPatientByFamilyNameAndGiven(String familyName, String Given);
	
	public String getAssessmentResult(PatientBean patient);
	
}
