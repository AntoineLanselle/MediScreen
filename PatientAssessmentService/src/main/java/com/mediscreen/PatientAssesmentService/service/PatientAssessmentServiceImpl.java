package com.mediscreen.PatientAssesmentService.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mediscreen.PatientAssesmentService.beans.NotesPatientBean;
import com.mediscreen.PatientAssesmentService.beans.PatientBean;
import com.mediscreen.PatientAssesmentService.exception.PatientNotFoundException;
import com.mediscreen.PatientAssesmentService.proxies.NotesPatientServiceProxy;
import com.mediscreen.PatientAssesmentService.proxies.PatientApiServiceProxy;

import feign.FeignException;

/**
 * Implementation of the PatientAssessmentService interface. This class provides
 * the actual implementation for patient assessment.
 * 
 * @author Antoine Lanselle
 */
@Service
public class PatientAssessmentServiceImpl implements PatientAssessmentService {

	private Logger logger = LoggerFactory.getLogger(PatientAssessmentServiceImpl.class);

	private final static List<String> TRIGGERS = Arrays.asList("Hemoglobin A1C", "Microalbumin", "Height", "Weight",
			"Smoker", "Abnormal", "Cholesterol", "Dizziness", "Relapse", "Reaction", "Antibodies");

	private final PatientApiServiceProxy patientProxy;

	private final NotesPatientServiceProxy notesProxy;

	public PatientAssessmentServiceImpl(PatientApiServiceProxy patientProxy, NotesPatientServiceProxy notesProxy) {
		this.patientProxy = patientProxy;
		this.notesProxy = notesProxy;
	}

	/**
	 * Retrieves a patient by their unique identifier.
	 *
	 * @param id The ID of the patient to retrieve.
	 * @return The PatientBean representing the patient with the given ID.
	 * @throws PatientNotFoundException If the patient with the given ID doesn't
	 *                                  exist.
	 */
	@Override
	public PatientBean getPatientById(int id) {
		logger.info("Attempting to retrieve patient with ID: " + id);
		try {
			PatientBean patient = patientProxy.getPatient(id);
			logger.info("Patient " + id + " retrieved successfully.");
			return patient;
		} catch (FeignException.NotFound ex) {
			String error = ex.getMessage() + " \n the Patient with this id doesn't exist.";
			logger.error("Error while retrieving patient " + id + " " + error);
			throw new PatientNotFoundException(error);
		}
	}

	/**
	 * Retrieves a patient by their family name and given name.
	 *
	 * @param familyName The family name of the patient to retrieve.
	 * @param given      The given name of the patient to retrieve.
	 * @return The PatientBean representing the patient with the given family name
	 *         and given name.
	 * @throws PatientNotFoundException If no patient with the given name exists or
	 *                                  if there are multiple patients with the same
	 *                                  name.
	 */
	@Override
	public PatientBean getPatientByFamilyNameAndGiven(String familyName, String given) {
		logger.info("Attempting to retrieve patient " + familyName + " " + given);
		List<PatientBean> patientList = this.patientProxy.searchPatients(given, familyName);
		if (patientList.size() > 1) {
			String error = "the Patient cannot be found because this name corresponds to several Patients.";
			logger.error(error);
			throw new PatientNotFoundException(error);
		} else if (patientList.size() < 1) {
			String error = "the Patient with this name doesn't exist.";
			logger.error(error);
			throw new PatientNotFoundException(error);
		} else {
			PatientBean patient = patientList.get(0);
			logger.info("Patient " + familyName + " " + given + " retrieved successfully.");
			return patient;
		}
	}

	/**
	 * Retrieves the assessment result for a given patient.
	 *
	 * @param patient The PatientBean representing the patient to assess.
	 * @return The assessment result as a String indicating the diabetes risk level
	 *         of the patient.
	 */
	@Override
	public String getAssessmentResult(PatientBean patient) {
		logger.info("Assessing diabetes risk for patient " + patient.getId());
		List<NotesPatientBean> notesList = notesProxy.getAllPatientNotes(patient.getId());
		int triggersNumber = searchTriggersInNotes(notesList);
		int agePatient = calculateAge(patient.getDob());
		String genderPatient = patient.getSex();
		String riskLevel = identifyRiskLevel(agePatient, triggersNumber, genderPatient);
		logger.info("Assessment completed for patient " + patient.getId() + " diabetes risk level: " + riskLevel);
		return "Patient: " + patient.getGiven() + " " + patient.getFamily() + " (age " + agePatient
				+ ") diabetes assessment is: " + riskLevel;
	}

	/**
	 * Calculates the age of a patient based on their date of birth.
	 *
	 * @param dob The date of birth of the patient.
	 * @return The age of the patient in years.
	 */
	private int calculateAge(LocalDate dob) {
		LocalDate todayDate = LocalDate.now();
		Period difference = Period.between(dob, todayDate);
		return difference.getYears();
	}

	/**
	 * Searches for triggers related to diabetes risk in the patient's notes.
	 *
	 * @param notesList The list of NotesPatientBean objects representing the
	 *                  patient's notes.
	 * @return The number of unique triggers found in the notes.
	 */
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

	/**
	 * Identifies the risk level of a patient based on age, trigger count, and
	 * gender.
	 *
	 * @param age           The age of the patient.
	 * @param triggerNumber The number of unique triggers found in the patient's
	 *                      notes.
	 * @param gender        The gender of the patient ("M" for male, "F" for
	 *                      female).
	 * @return The risk level as a String ("None", "Borderline", "In Danger", "Early
	 *         Onset", or "Indeterminate").FF
	 */
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
