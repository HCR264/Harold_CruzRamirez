package com.chakray.prueba_api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chakray.prueba_api.config.UserData;
import com.chakray.prueba_api.dto.request.LoginRequestDTO;
import com.chakray.prueba_api.model.User;
import com.chakray.prueba_api.shared.exception.BusinessException;
import com.chakray.prueba_api.util.AesEncryptionUtil;
import com.chakray.prueba_api.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final UserData userData;
	private final AesEncryptionUtil aesEncryptionUtil;
	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) throws Exception {
		User user = userData.findByTaxId(loginRequest.getTaxId())
				.orElseThrow(() -> new BusinessException("No existe el usuario"));
		String decryptedPassword = aesEncryptionUtil.decrypt(user.getPassword());
		if (!decryptedPassword.equals(loginRequest.getPassword())) {
			throw new BusinessException("No coinciden");
		}
		String token = jwtUtil.generateToken(loginRequest.getTaxId());
		return ResponseEntity.ok(Map.of("token", token));
	}
}