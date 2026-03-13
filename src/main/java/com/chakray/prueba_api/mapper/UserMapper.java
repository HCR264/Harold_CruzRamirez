package com.chakray.prueba_api.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.chakray.prueba_api.dto.request.UserRequestDTO;
import com.chakray.prueba_api.dto.response.UserResponseDTO;
import com.chakray.prueba_api.dto.update.UserUpdateDTO;
import com.chakray.prueba_api.model.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Clase para realizar los mapeso entre clases de tipo User
 */
@Component
@Data
@RequiredArgsConstructor
public class UserMapper {
	private final AddressMapper addressMapper;

	/**
	 * Método para convertir un UserRequestDTO a un User
	 * 
	 * @param userRequest La clase RequestDTO
	 * @return Entity
	 */
	public User requestToEntity(UserRequestDTO userRequest) {
		return User.builder().email(userRequest.getEmail().trim()).name(userRequest.getName().trim())
				.phone(userRequest.getPhone().trim()).password(userRequest.getPassword().trim())
				.taxId(userRequest.getTaxId().trim())
				.addresses(addressMapper.requestListToEntityList(userRequest.getAddresses())).build();
	}

	/**
	 * * Método para convertir un User a un UserResponseDTO
	 * 
	 * @param user La clase Entity
	 * @return Response
	 */
	public UserResponseDTO entityToResponse(User user) {
		return UserResponseDTO.builder().id(user.getId()).email(user.getEmail()).name(user.getName())
				.phone(user.getPhone()).taxId(user.getTaxId()).createdAt(user.getCreatedAt())
				.addresses(addressMapper.entityListToResponseList(user.getAddresses())).build();
	}

	/**
	 * * Método para convertir una lista con Users a una lista con UsersResponseDTO
	 * 
	 * @param users La lista con Users
	 * @return Lista con UserResponseDTO
	 */
	public List<UserResponseDTO> entityListToResponseList(List<User> users) {
		List<UserResponseDTO> usersResponse = new ArrayList<>();
		UserResponseDTO userResponse;
		for (User user : users) {
			userResponse = entityToResponse(user);
			usersResponse.add(userResponse);
		}
		return usersResponse;
	}
}
