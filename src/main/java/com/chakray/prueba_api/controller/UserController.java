package com.chakray.prueba_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chakray.prueba_api.dto.request.UserRequestDTO;
import com.chakray.prueba_api.dto.response.UserResponseDTO;
import com.chakray.prueba_api.dto.update.UserUpdateDTO;
import com.chakray.prueba_api.service.UserServiceI;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserServiceI userService;

	@GetMapping
	public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam(required = false) String sortedBy,
			@RequestParam(required = false) String filter) {
		return ResponseEntity.ok(userService.getUsers(sortedBy, filter));
	}

	@PostMapping
	public ResponseEntity<UserResponseDTO> saveUser(@Valid @RequestBody UserRequestDTO userRequestDTO)
			throws Exception {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
			@Valid @RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
		return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
