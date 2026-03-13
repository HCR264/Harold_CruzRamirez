package com.chakray.prueba_api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Request para los datos de una direccion
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {
	private String name;
	private String street;
	private String countryCode;
}
