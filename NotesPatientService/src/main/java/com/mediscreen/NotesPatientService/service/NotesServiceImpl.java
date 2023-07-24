package com.mediscreen.NotesPatientService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mediscreen.NotesPatientService.domain.Notes;
import com.mediscreen.NotesPatientService.domain.SequenceCounter;
import com.mediscreen.NotesPatientService.dto.NotesDto;
import com.mediscreen.NotesPatientService.exception.NotesNotFoundException;
import com.mediscreen.NotesPatientService.repository.NotesRepository;

/**
 * Implementation of the NotesService interface. This class provides the actual
 * implementation for managing notes.
 * 
 * @author Antoine Lanselle
 */
@Service
public class NotesServiceImpl implements NotesService {

	private Logger logger = LoggerFactory.getLogger(NotesServiceImpl.class);

	@Autowired
	private NotesRepository notesRepository;

	@Autowired
	private MongoOperations mongoOperations;

	/**
	 * Retrieves a specific NotesDto by its notesId.
	 *
	 * @param notesId The unique identifier of the notes to retrieve.
	 * 
	 * @return The NotesDto representing the notes with the specified notesId.
	 * @throws NotesNotFoundException if the notes with the specified notesId is not
	 *                                found in the database.
	 */
	public NotesDto getNotesById(int notesId) {
		logger.info("Trying to find notes by id in data base.");

		Notes notes = notesRepository.findByNotesId(notesId);
		if (notes != null) {
			logger.info("Notes has been found in data base.");
			return new NotesDto(notes);
		} else {
			String error = "Notes: " + notesId + " not found in data base !";
			logger.error(error);
			throw new NotesNotFoundException(error);
		}
	}

	/**
	 * Retrieves a list of NotesDto associated with a specific patient ID, ordered
	 * by date of the notes in descending order.
	 *
	 * @param patientId The ID of the patient for whom to retrieve the notes.
	 * 
	 * @return A list of NotesDto associated with the given patient ID, ordered by
	 *         date of the notes in descending order.
	 */
	public List<NotesDto> findByPatientId(int patientId) {
		logger.info("Getting all notes of patient " + patientId + " in data base.");
		List<Notes> notesList = notesRepository.findByPatientIdOrderByDateTimeNoteDesc(patientId);
		List<NotesDto> notesDtos = notesList.stream().map(NotesDto::new).collect(Collectors.toList());
		return notesDtos;
	}

	/**
	 * Creates and adds a new notes entry in the database.
	 *
	 * @param notesDto The NotesDto representing the notes to be created.
	 */
	public void createNotes(NotesDto notesDto) {
		logger.info("Adding notes in data base.");
		notesDto.setDateTimeNote(LocalDateTime.now());

		Notes notes = new Notes(notesDto);
		notes.setNotesId(getNextSequenceId());

		notesRepository.save(notes);
	}

	/**
	 * Updates an existing notes entry in the database.
	 *
	 * @param notesId     The identifier of the notes to be updated.
	 * @param updateNotes The NotesDto containing the updated notes data.
	 * 
	 * @throws NotesNotFoundException if the notes with the specified notesId is not
	 *                                found in the database.
	 */
	public void updateNotes(int notesId, NotesDto updateNotes) {
		logger.info("Trying to update notes in data base.");

		Notes notes = notesRepository.findByNotesId(notesId);
		if (notes == null) {
			String error = "Notes: " + notesId + " not found in data base !";
			logger.error(error);
			throw new NotesNotFoundException(error);
		} else {
			logger.info("Updating notes in data base.");
			notes.setNotes(updateNotes.getE());
			deleteNotes(notesId);
			notesRepository.save(notes);
		}
	}

	/**
	 * Deletes an existing notes entry from the database.
	 *
	 * @param notesId The identifier of the notes to be deleted.
	 * 
	 * @throws NotesNotFoundException if the notes with the specified notesId is not
	 *                                found in the database.
	 */
	public void deleteNotes(int notesId) {
		logger.info("Trying to delete patient in data base.");

		Notes notes = notesRepository.findByNotesId(notesId);
		if (notes == null) {
			String error = "Notes: " + notesId + " not found in data base !";
			logger.error(error);
			throw new NotesNotFoundException(error);
		} else {
			logger.info("Deleting notes in data base.");
			notesRepository.delete(notes);
		}
	}

	/**
	 * Retrieves the next sequence ID for creating a new notes entry in the
	 * database.
	 *
	 * @return The next available sequence ID for notes.
	 */
	private int getNextSequenceId() {
		Query query = new Query(Criteria.where("_id").is("notes_sequence"));
		Update update = new Update().inc("seq", 1);
		FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

		SequenceCounter counter = mongoOperations.findAndModify(query, update, options, SequenceCounter.class);
		return counter.getSeq();
	}

}
