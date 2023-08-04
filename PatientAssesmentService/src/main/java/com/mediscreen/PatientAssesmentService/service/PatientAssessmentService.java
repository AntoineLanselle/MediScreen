package com.mediscreen.PatientAssesmentService.service;

import com.mediscreen.PatientAssesmentService.beans.PatientBean;

public interface PatientAssessmentService {

	public PatientBean getPatientById(int id);
	
	public PatientBean getPatientByFamilyNameAndGiven(String familyName, String Given);
	
	public String getAssessmentResult(PatientBean patient);
	
}
