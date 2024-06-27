package com.petstore.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		super("名前とステータスは入力必須です。");
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
