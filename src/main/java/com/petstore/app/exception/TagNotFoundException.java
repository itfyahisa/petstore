package com.petstore.app.exception;

public class TagNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagNotFoundException(Long id) {
		super("Tag : " + id + " が見つかりませんでした。");
	}
	
}
