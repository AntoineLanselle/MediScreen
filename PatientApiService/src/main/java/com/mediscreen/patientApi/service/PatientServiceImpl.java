package com.mediscreen.patientApi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mediscreen.patientApi.domain.Patient;
import com.mediscreen.patientApi.dto.PatientDto;
import com.mediscreen.patientApi.exception.PatientNotFoundException;
import com.mediscreen.patientApi.repository.PatientRepository;

/**
 * Implementation of the PatientService interface. This class provides the
 * actual implementation for managing patients.
 * 
 * @author Antoine Lanselle
 */
@Service
public class PatientServiceImpl implements PatientService {

	private Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;

	/**
	 * Retrieves a list of all patients.
	 *
	 * @return a list of PatientDto objects representing all patients.
	 */
	@Override
	public List<PatientDto> findAllPatients() {
		logger.info("Getting all patients in data base.");

		List<Patient> allPatients = patientRepository.findAll();
		List<PatientDto> allPatientsDto = new ArrayList<>();
		for (Patient patient : allPatients)
			allPatientsDto.add(new PatientDto(patient));

		return allPatientsDto;
	}

	/**
	 * Retrieves a patient by their ID.
	 *
	 * @param patientId the ID of the patient to retrieve.
	 * 
	 * @return the PatientDto object representing the patient with the specified ID.
	 * @throws PatientNotFoundException if the patient with the specified ID is not
	 *                                  found.
	 */
	@Override
	public PatientDto findPatientById(int patientId) throws PatientNotFoundException {
		logger.info("Trying to find patient by id in data base.");

		Patient patient = patientRepository.findById(patientId);
		if (patient == null) {
			String error = "Patient: " + patientId + " not found in data base !";
			logger.error(error);
			throw new PatientNotFoundException(error);
		} else {
			logger.info("Patient has been found in data base.");
			return new PatientDto(patient);
		}
	}

	/**
	 * Adds a new patient to the database.
	 *
	 * @param patientDto the PatientDto object representing the patient to add.
	 */
	@Override
	public void addPatient(PatientDto patientDto) {
		logger.info("Adding patient in data base.");
		patientRepository.save(new Patient(patientDto));
	}

	/**
	 * Updates an existing patient with new information in the database.
	 *
	 * @param patientId       the ID of the patient to update.
	 * @param newPatientInfos the PatientDto object containing the updated patient
	 *                        information.
	 * 
	 * @throws PatientNotFoundException if the patient with the specified ID is not
	 *                                  found.
	 */
	@Override
	public void updatePatient(int patientId, PatientDto newPatientInfos) throws PatientNotFoundException {
		logger.info("Trying to update patient in data base.");

		Patient patient = patientRepository.findById(patientId);

		if (patient == null) {

			String error = "Patient: " + patientId + " not found in data base !";
			logger.error(error);
			throw new PatientNotFoundException(error);

		} else {

			patient.setFirstname(newPatientInfos.getGiven());
			patient.setLastname(newPatientInfos.getFamily());
			patient.setDateOfBirth(newPatientInfos.getDob());
			patient.setGender(newPatientInfos.getSex());
			patient.setAddress(newPatientInfos.getAddress());
			patient.setPhone(newPatientInfos.getPhone());

			logger.info("Updating patient in data base.");
			patientRepository.save(patient);
		}
	}

	/**
	 * Deletes a patient from the database.
	 *
	 * @param patientId the ID of the patient to delete.
	 * 
	 * @throws PatientNotFoundException if the patient with the specified ID is not
	 *                                  found.
	 */
	@Override
	public void deletePatient(int patientId) throws PatientNotFoundException {
		logger.info("Trying to delete patient in data base.");

		PatientDto patient = findPatientById(patientId);
		if (patient == null) {
			String error = "Patient: " + patientId + " not found in data base !";
			logger.error(error);
			throw new PatientNotFoundException(error);
		} else {
			logger.info("Deleting patient in data base.");
			patientRepository.deleteById(patientId);
		}
	}

}
