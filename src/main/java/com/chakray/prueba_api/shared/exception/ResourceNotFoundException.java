package com.chakray.prueba_api.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Clase Exception para lanzar NOT_FOUND
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -188689838792963292L;

	/**
	 * Constructor
	 * @param message El mensaje de errror
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
