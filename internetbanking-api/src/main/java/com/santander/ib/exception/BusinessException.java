package com.santander.ib.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessException extends RuntimeException {
    
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String message) {
        super(message);
    }
}