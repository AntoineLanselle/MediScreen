package com.mediscreen.userInterface.beans;

import java.time.LocalDateTime;

/**
 * NotesPatientBean represents a patient's notes data used in the user
 * interface.
 * 
 * @author Antoine Lanselle
 */
public class NotesPatientBean {

	private int notesId;
	private int patId;
	private LocalDateTime dateTimeNote;
	private String e;

	public NotesPatientBean() {
	}

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int notesId) {
		this.notesId = notesId;
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
