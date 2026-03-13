package com.chakray.prueba_api.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chakray.prueba_api.shared.exception.GlobalExceptionHandler;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	@Value("${JWT_EXPIRATION}")
	private Long jwtExpiration;
	@Value("${JWT_SECRET}")
	private String jwtSecret;

	public String generateToken(String taxId) {
		Key key = getSigningKey();
		return Jwts.builder().subject(taxId).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(key).compact();
	}

	public boolean validateToken(String token) {
		Key key = getSigningKey();
		try {
			Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
			return true;
		} catch (SignatureException e) {
			log.warn("Firma JWT inválida: " + e.getMessage());
		} catch (MalformedJwtException e) {
			log.warn("Token JWT malformado: " + e.getMessage());
		} catch (ExpiredJwtException e) {
			log.warn("Token JWT expirado: " + e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.warn("Token JWT no soportado: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			log.warn("Claims JWT vacíos: " + e.getMessage());
		}
		return false;
	}

	public String extractTaxId(String token) {
		Key key = getSigningKey();
		try {
			Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
			return claims.getSubject();
		} catch (Exception e) {
			throw new RuntimeException("Error extracting taxId from token: " + e.getMessage());
		}
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}
}
