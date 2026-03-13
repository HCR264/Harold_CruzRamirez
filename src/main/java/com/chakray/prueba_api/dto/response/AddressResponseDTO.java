package com.chakray.prueba_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Response para los datos de una direccion
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {
	private Long id;
	private String name;
	private String street;
	private String countryCode;
}
