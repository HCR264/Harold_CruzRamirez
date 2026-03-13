package com.chakray.prueba_api.model;

import java.util.List;
import java.util.UUID;

//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase entidad para mapear los registros de la tabla <b>USERS<\b>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
//@Table(name = "users")
public class User {
//	@Id
//	@GeneratedValue(strategy = GenerationType.UUID)
//	@Column(name = "id", updatable = false)
	private UUID id;

//	@Column(name = "email", nullable = false, length = 50)
	private String email;

//	@Column(name = "name", nullable = false, length = 200)
	private String name;

//	@Column(name = "phone", nullable = false, length = 20)
	private String phone;

//	@Column(name = "password", nullable = false, length = 255)
	private String password;

//	@Column(name = "tax_id", nullable = false, length = 13, unique = true)
	private String taxId;

//	@Column(name = "created_at", nullable = false, length = 20)
	private String createdAt;

//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> addresses;
}
