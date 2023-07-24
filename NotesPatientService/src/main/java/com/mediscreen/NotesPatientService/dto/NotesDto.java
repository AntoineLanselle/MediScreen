package com.mediscreen.NotesPatientService.dto;

import java.time.LocalDateTime;

import com.mediscreen.NotesPatientService.domain.Notes;

/**
 * Data Transfer Object (DTO) for representing a Notes. Contains notes
 * information used for communication between different layers of the
 * application.
 * 
 * @author Antoine Lanselle
 */
public class NotesDto {

	private int notesId;
	private int patId;
	private LocalDateTime dateTimeNote;
	private String e;

	public NotesDto() {
	}

	public NotesDto(int patId, String e) {
		this.patId = patId;
		this.e = e;
	}
	
	public NotesDto(Notes notes) {
		this.notesId = notes.getNotesId();
		this.patId = notes.getPatientId();
		this.dateTimeNote = notes.getDateTimeNote();
		this.e = notes.getNotes();
	}

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int id) {
		this.notesId = id;
	}

	public int getPatId() {
		return patId;
	}

	public void setPatId(int patId) {
		this.patId = patId;
	}

	public LocalDateTime getDateTimeNote() {
		return dateTimeNote;
	}

	public void setDateTimeNote(LocalDateTime dateTimeNote) {
		this.dateTimeNote = dateTimeNote;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

}
