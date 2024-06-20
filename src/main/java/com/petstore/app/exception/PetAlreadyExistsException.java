package com.petstore.app.exception;

public class PetAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    public PetAlreadyExistsException(String message) {
        super(message);
    }
	
}
