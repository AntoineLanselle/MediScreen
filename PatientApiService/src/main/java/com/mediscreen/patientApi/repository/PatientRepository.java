package com.mediscreen.patientApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.patientApi.domain.Patient;

/**
 * Repository interface for managing patients in the database. This interface
 * extends the JpaRepository interface provided by Spring Data JPA, which
 * provides CRUD operations for the Patient entity.
 *
 * @author Antoine Lanselle
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

	public Patient findById(int id);

}
