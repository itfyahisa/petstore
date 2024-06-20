package com.petstore.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CategorytNotFoundExceptionControllerAdvice {
	
	@ResponseBody
	@ExceptionHandler(CategoryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String CategoryNotFoundHandler(CategoryNotFoundException ex) {
		return ex.getMessage();
	}
	
}
