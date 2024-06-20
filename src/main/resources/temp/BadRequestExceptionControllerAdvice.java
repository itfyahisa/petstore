package com.petstore.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class BadRequestExceptionControllerAdvice{
	
	@ResponseBody
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String BadRequestHandler(BadRequestException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String HttpMessageNotReadablehandler(HttpMessageNotReadableException ex, WebRequest request) {
		String errorMessage = "リクエストボディのフォーマットが不正です。ステータスは 'available', 'pending', 'sold' です。";
        return errorMessage;
	}
	

}
