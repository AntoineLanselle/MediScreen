package com.mediscreen.PatientAssesmentService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mediscreen.PatientAssesmentService.proxies.NotesPatientServiceProxy;
import com.mediscreen.PatientAssesmentService.proxies.PatientApiServiceProxy;


@Service
public class PatientAssessmentServiceImpl implements PatientAssessmentService {
	
	private Logger logger = LoggerFactory.getLogger(PatientAssessmentServiceImpl.class);
	
	private final PatientApiServiceProxy patientProxy;

	private final NotesPatientServiceProxy notesProxy;
	
	public PatientAssessmentServiceImpl(PatientApiServiceProxy patientProxy, NotesPatientServiceProxy notesProxy) {
		this.patientProxy = patientProxy;
		this.notesProxy = notesProxy;
	}
	
	

}
