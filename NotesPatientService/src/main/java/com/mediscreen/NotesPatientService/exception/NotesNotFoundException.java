package com.mediscreen.NotesPatientService.exception;

/**
 * Exception thrown when a notes is not found. This exception is typically
 * thrown when attempting to retrieve a notes by ID and no notes with the
 * specified ID is found in the database.
 * 
 * @author Antoine Lanselle
 */
public class NotesNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	/**
	 * Constructs a NotesNotFoundException with the specified message.
	 *
	 * @param message the message explaining the exception.
	 */
	public NotesNotFoundException(String message) {
		super(message);
	}

}
