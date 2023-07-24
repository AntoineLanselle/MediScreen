package com.mediscreen.NotesPatientService.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mediscreen.NotesPatientService.domain.Notes;

@Repository
public interface NotesRepository extends MongoRepository<Notes, Integer> {

	//trier par date ?  findByPatientIdOrderByDateDesc
	public List<Notes> findByPatientId(int patientId);

	public Notes findByNotesId(int notesId);
	
}
