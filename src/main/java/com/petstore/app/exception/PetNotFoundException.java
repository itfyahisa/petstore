package com.petstore.app.exception;

public class PetNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PetNotFoundException(Long id) {
		super("Pet : " + id + " が見つかりませんでした。");
	}
	
}
