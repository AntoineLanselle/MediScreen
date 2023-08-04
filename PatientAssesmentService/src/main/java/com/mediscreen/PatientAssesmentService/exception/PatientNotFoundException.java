package com.mediscreen.PatientAssesmentService.exception;

/**
 * Exception thrown when a patient is not found. This exception is typically
 * thrown when attempting to retrieve a patient by ID and no patient with the
 * specified ID is found in the database.
 * 
 * @author Antoine Lanselle
 */
public class PatientNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	/**
	 * Constructs a PatientNotFoundException with the specified message.
	 *
	 * @param message the message explaining the exception.
	 */
	public PatientNotFoundException(String message) {
		super(message);
	}

}
