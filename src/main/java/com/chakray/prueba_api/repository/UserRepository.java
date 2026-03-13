package com.chakray.prueba_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chakray.prueba_api.model.User;

/**
 * Interface para realizar las consultas a la tabla <b>USERS<\b>
 */
public interface UserRepository extends JpaRepository<User, UUID> {
	/**
	 * Método para encontrar un <b>usuario<\b> de la base de datos
	 * 
	 * @param taxId El tax_id del usuario
	 * @return Optional con el usuario encontrado
	 */
	Optional<User> findByTaxId(String taxId);

	/**
	 * Método para verificar sí un <b>usuario<\b> existe en la base de datos
	 * 
	 * @param taxId El tax_id del usuario
	 * @return <i>True<\i> si encuentra el usuario. <i>False<\i> en caso contrario
	 */
	Boolean existsByTaxId(String taxId);

	/**
	 * Método para verificar sí un <b>usuario<\b> existe en la base de datos
	 * 
	 * @param email El email del usuario
	 * @return <i>True<\i> si encuentra el usuario. <i>False<\i> en caso contrario
	 */
	Boolean existsByEmail(String email);

	/**
	 * Método para comprobar sí existe un tax_id en usuarios con un id distinto
	 * 
	 * @param taxId El tax_id
	 * @param id    El id
	 * @return True si hay una coincidencia, False en caso contrario
	 */
	Boolean existsByTaxIdAndIdNot(String taxId, UUID id);
}
