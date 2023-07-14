package com.mediscreen.patientApi.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException  {
	
	private static final long serialVersionUID = 2L;

	/**
	 * Construit une PatientNotFoundException.
	 * 
	 * @param le message de l'exception.
	 * 
	 */
	public PatientNotFoundException(String message) {
		super(message);
	}

}
