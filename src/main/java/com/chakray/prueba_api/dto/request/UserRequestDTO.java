package com.chakray.prueba_api.dto.request;

import java.util.List;

import com.chakray.prueba_api.util.validation.AndresFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
	@AndresFormat
	private String phone;
	@NotBlank
	private String password;
	@NotBlank
	@Pattern(regexp = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$")
	private String taxId;
	@NotNull
	private List<AddressRequestDTO> addresses;
}
