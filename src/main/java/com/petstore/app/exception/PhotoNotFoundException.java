package com.petstore.app.exception;

public class PhotoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PhotoNotFoundException(Long id) {
		super("Photo : " + id + " が見つかりませんでした。");
	}
	
}
