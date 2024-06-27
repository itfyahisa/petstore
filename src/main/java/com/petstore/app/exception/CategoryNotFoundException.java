package com.petstore.app.exception;

public class CategoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(Long id) {
		super("Category : " + id + " が見つかりませんでした。");
	}
	
}

