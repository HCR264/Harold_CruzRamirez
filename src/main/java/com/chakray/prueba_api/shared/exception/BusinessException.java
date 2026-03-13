package com.chakray.prueba_api.shared.exception;

/**
 * Clase Exception para lanzar errores de negocio
 */
public class BusinessException extends RuntimeException {
	/**
	 * Serial de la clase
	 */
	private static final long serialVersionUID = -6674237824454748622L;

	/**
	 * @param message El mensaje a mostrar
	 */
	public BusinessException(String message) {
		super(message);
	}

}
