package com.example.minixrm.backend.core.facade.util;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	
	BAD_REQUEST_JSON       (HttpStatus.BAD_REQUEST),
	VALIDATION_FAILED      (HttpStatus.BAD_REQUEST),
	INTERNAL_SERVER_ERROR  (HttpStatus.INTERNAL_SERVER_ERROR),
	PARTNER_TAG_NOT_FOUND  (HttpStatus.UNPROCESSABLE_ENTITY),
	PARTNER_NOT_FOUND      (HttpStatus.UNPROCESSABLE_ENTITY),
	ACTIVITY_NOT_FOUND     (HttpStatus.UNPROCESSABLE_ENTITY),
	ENTITY_NOT_FOUND       (HttpStatus.NOT_FOUND),
	PARTNER_INACTIVE       (HttpStatus.UNPROCESSABLE_ENTITY),
	CONCURRENT_MODIFICATION(HttpStatus.CONFLICT),
	;
	
	private final HttpStatus httpStatus;
	
	private ErrorCode(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}