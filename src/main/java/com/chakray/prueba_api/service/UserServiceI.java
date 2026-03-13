package com.chakray.prueba_api.service;

import java.util.List;
import java.util.UUID;

import com.chakray.prueba_api.dto.request.UserRequestDTO;
import com.chakray.prueba_api.dto.response.UserResponseDTO;
import com.chakray.prueba_api.dto.update.UserUpdateDTO;

/**
 * Interfaz con los métodos del Service para usuarios
 */
public interface UserServiceI {
	/**
	 * Método para obtener todos los usuarios guardados
	 * 
	 * @param sortedBy El críterio de ordenamiento
	 * @param filter   El tipo de filtro
	 * @return Lista de Usuarios encontrados
	 */
	List<UserResponseDTO> getUsers(String sortedBy, String filter);

	/**
	 * Método para crear un nuevo usuario
	 * 
	 * @param userRequest El request con los datos del nuevo usuario
	 * @return Response con un los datos del nuevo usuario
	 * @throws Exception
	 */
	UserResponseDTO createUser(UserRequestDTO userRequest) throws Exception;

	/**
	 * Método para actualizar un usuario existente
	 * 
	 * @param id         El ID del usuario a editar
	 * @param userUpdate El update con los datos del nuevo usuario
	 * @return Response con los datos actualizados del usuario
	 * @throws Exception 
	 */
	UserResponseDTO updateUser(UUID id, UserUpdateDTO userUpdate) throws Exception;

	/**
	 * Método para eliminar un usuario existente
	 * 
	 * @param id El ID del usuario que se desea eliminar
	 */
	void deleteUser(UUID id);

}
