package com.mediscreen.patientApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.patientApi.domain.Patient;

/**
 * Repository of Patients.
 *
 * @author Antoine Lanselle
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	public Patient findById(int id);

}
