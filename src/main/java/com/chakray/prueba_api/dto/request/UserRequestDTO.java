package com.chakray.prueba_api.dto.request;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Request para los datos de un usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	@NotBlank
	private String phone;
	@NotBlank
	private String password;
	@NotBlank
	private String taxId;
	@NotNull
	private List<AddressRequestDTO> addresses;
}
