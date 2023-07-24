package com.mediscreen.NotesPatientService.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mediscreen.NotesPatientService.dto.NotesDto;

@Document(collection = "notes")
public class Notes {

	@Id
	private int notesId;
	
	@Field(value = "patientId")
    private int patientId;
	
	@Field("dateTime")
	private LocalDateTime dateTimeNote;
	
    @Field(value = "notes")
    private String notes;

	public Notes() {
	}

	public Notes(int patientId, LocalDateTime dateTimeNote, String notes) {
		this.patientId = patientId;
		this.dateTimeNote = dateTimeNote;
		this.notes = notes;
	}

	public Notes(NotesDto notesDto) {
		this.patientId = notesDto.getPatId();
		this.dateTimeNote = notesDto.getDateTimeNote();
		this.notes = notesDto.getE();
	}

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int notesId) {
		this.notesId = notesId;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public LocalDateTime getDateTimeNote() {
		return dateTimeNote;
	}

	public void setDateTimeNote(LocalDateTime dateTimeNote) {
		this.dateTimeNote = dateTimeNote;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	  
}
