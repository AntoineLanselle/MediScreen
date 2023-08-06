package com.mediscreen.PatientAssesmentService.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.mediscreen.PatientAssesmentService.beans.NotesPatientBean;
import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.proxies.NotesPatientServiceProxy;
import com.mediscreen.PatientAssesmentService.proxies.PatientApiServiceProxy;

import feign.FeignException;

@Service
public class PatientAssessmentServiceImpl implements PatientAssessmentService {

	// private Logger logger =
	// LoggerFactory.getLogger(PatientAssessmentServiceImpl.class);

	private final static List<String> TRIGGERS = Arrays.asList("Hemoglobin A1C", "Microalbumin", "Height", "Weight",
			"Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies");

	private final PatientApiServiceProxy patientProxy;

	private final NotesPatientServiceProxy notesProxy;

	public PatientAssessmentServiceImpl(PatientApiServiceProxy patientProxy, NotesPatientServiceProxy notesProxy) {
		this.patientProxy = patientProxy;
		this.notesProxy = notesProxy;
	}

	@Override
	public PatientBean getPatientById(int id) {
		// TODO logger
		try {
			return patientProxy.getPatient(id);
		} catch (FeignException.NotFound ex) {
			String error = ex.getMessage() + " \n the Patient with this id doesn't exist.";
			throw new PatientNotFoundException(error);
		}
	}

	@Override
	public PatientBean getPatientByFamilyNameAndGiven(String familyName, String given) {
		// TODO logger
		List<PatientBean> patientList = this.patientProxy.searchPatients(given, familyName);
		if (patientList.size() > 1) {
			String error = "the Patient cannot be found because this name corresponds to several Patients.";
			throw new PatientNotFoundException(error);
		} else if (patientList.size() < 1) {
			String error = "the Patient with this name doesn't exist.";
			throw new PatientNotFoundException(error);
		} else {
			return patientList.get(0);
		}
	}

	@Override
	public String getAssessmentResult(PatientBean patient) {
		// TODO logger
		List<NotesPatientBean> notesList = notesProxy.getAllPatientNotes(patient.getId());
		int triggersNumber = searchTriggersInNotes(notesList);
		int agePatient = calculateAge(patient.getDob());
		String genderPatient = patient.getSex();
		String riskLevel = identifyRiskLevel(agePatient, triggersNumber, genderPatient);
		return "Patient: " + patient.getGiven() + " " + patient.getFamily() + " (age " + agePatient
				+ ") diabetes assessment is: " + riskLevel;
	}

	private int calculateAge(LocalDate dob) {
		LocalDate todayDate = LocalDate.now();
		Period difference = Period.between(dob, todayDate);
		return difference.getYears();
	}

	private int searchTriggersInNotes(List<NotesPatientBean> notesList) {
		List<String> triggersPresent = new ArrayList<String>();
		for (NotesPatientBean notes : notesList) {
			for (String trigger : TRIGGERS) {
				if (notes.getE().contains(trigger) && !triggersPresent.contains(trigger)) {
					triggersPresent.add(trigger);
				}
			}
		}
		return triggersPresent.size();
	}

	private String identifyRiskLevel(int age, int triggerNumber, String gender) {
		if (triggerNumber < 2) {
			return "None";
		} else if (triggerNumber == 2 && age > 30) {
			return "Borderline";
		} else if ((gender.equals("M") && age < 30 && triggerNumber < 5)
				|| (gender.equals("F") && age < 30 && triggerNumber < 7) || (age > 30 && triggerNumber < 8)) {
			return "In Danger";
		} else if ((gender.equals("M") && age < 30 && triggerNumber >= 5)
				|| (gender.equals("F") && age < 30 && triggerNumber >= 7) || (age > 30 && triggerNumber >= 8)) {
			return "Early Onset";
		}
		return "Indeterminate";
	}

}
