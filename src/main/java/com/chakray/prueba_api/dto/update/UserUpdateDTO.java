package com.chakray.prueba_api.dto.update;

import java.util.List;

import com.chakray.prueba_api.dto.request.AddressRequestDTO;
import com.chakray.prueba_api.util.validation.AndresFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase Update para los datos de un usuario
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDTO {
	@Email
	private String email;
	private String name;
	@AndresFormat
	private String phone;
	private String password;
	@Pattern(regexp = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$")
	private String taxId;
	private List<AddressRequestDTO> addresses;
}
