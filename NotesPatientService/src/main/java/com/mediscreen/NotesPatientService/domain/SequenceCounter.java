package com.mediscreen.NotesPatientService.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a SequenceCounter entity in the system. Stores the counter of
 * notes's id.
 *
 * @author Antoine Lanselle
 */
@Document(collection = "notes_sequence")
public class SequenceCounter {

	@Id
	private String id;

	private int seq;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}