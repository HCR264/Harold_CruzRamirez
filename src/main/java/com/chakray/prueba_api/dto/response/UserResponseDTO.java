package com.chakray.prueba_api.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Response para los datos de un usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
	private UUID id;
	private String email;
	private String name;
	private String phone;
	private String taxId;
	private String createdAt;
	private List<AddressResponseDTO> addresses;
}
