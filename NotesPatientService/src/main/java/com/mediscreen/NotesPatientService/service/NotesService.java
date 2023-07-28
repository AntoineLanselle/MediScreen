package com.mediscreen.NotesPatientService.service;

import java.util.List;

import com.mediscreen.NotesPatientService.dto.NotesDto;

/**
 * Service interface for managing notes.
 * 
 * @author Antoine Lanselle
 */
public interface NotesService {

	public NotesDto getNotesById(int notesId);
	
	public List<NotesDto> findByPatientId(int patientId);
	
	public void createNotes(NotesDto notesDto);
	
	public void updateNotes(NotesDto updateNotes);
	
	public void deleteNotes(int notesId);
	
}
