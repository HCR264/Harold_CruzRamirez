package com.chakray.prueba_api.shared.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Getter;
import lombok.Setter;

/**
 * Global Exception Handler class
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle Resource Not Found Exception
	 * 
	 * @param ex The Resource Not Found Exception
	 * @return ResponseEntity with HttpCode 402
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		log.warn("Resourse Not Fount: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(402, ex.getMessage()));
	}

	/**
	 * Handle Business exception
	 * 
	 * @param ex The Business Exception
	 * @return ResponseEntity with HttpCode 422
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBussinesException(BusinessException ex) {
		log.warn("Bussisnes Error: {}", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ErrorResponse(422, ex.getMessage()));
	}

	/**
	 * Handle General Exception
	 * 
	 * @param ex The General Exception
	 * @return ResponseEntity with HttpCode 500
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
		log.error("Error: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Server Error"));
	}

	/**
	 * ErrorResponse class
	 */
	@Getter
	public static class ErrorResponse {
		private int status;
		private String mesagge;
		private LocalDateTime timestamp = LocalDateTime.now();
		@Setter
		private Map<String, String> errores;

		/**
		 * Class constructor
		 * 
		 * @param status  The status
		 * @param message The message
		 */
		public ErrorResponse(int status, String message) {
			this.status = status;
			this.mesagge = message;
		}
	}
}
