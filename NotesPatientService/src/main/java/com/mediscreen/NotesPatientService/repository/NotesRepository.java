package com.mediscreen.NotesPatientService.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.NotesPatientService.domain.Notes;

/**
 * Repository interface for managing Notes entities in the MongoDB database.
 *
 * The repository provides methods to perform CRUD operations on the Notes
 * collection in the database.
 *
 * @author Antoine Lanselle
 */
@Repository
public interface NotesRepository extends MongoRepository<Notes, Integer> {

	public List<Notes> findByPatientIdOrderByDateTimeNoteDesc(int patientId);

	public Notes findByNotesId(int notesId);

}
