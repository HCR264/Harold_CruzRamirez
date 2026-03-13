package com.chakray.prueba_api.dto.update;

import java.util.List;

import com.chakray.prueba_api.dto.request.AddressRequestDTO;

import jakarta.validation.constraints.Email;
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
	private String phone;
	private String password;
	private String taxId;
	private List<AddressRequestDTO> addresses;
}
