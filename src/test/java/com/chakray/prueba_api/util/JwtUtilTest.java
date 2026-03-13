package com.chakray.prueba_api.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "claveSecretaDe32CaracteresExactos!");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiration", 86400000L);
    }

    @Test
    void generateToken_returnsNonNullToken() {
        String token = jwtUtil.generateToken("CARL900101AB1");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void validateToken_returnsTrue_whenTokenIsValid() {
        String token = jwtUtil.generateToken("CARL900101AB1");
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void validateToken_returnsFalse_whenTokenIsInvalid() {
        assertFalse(jwtUtil.validateToken("esto.no.es.un.token"));
    }

    @Test
    void extractTaxId_returnsCorrectValue() {
        String taxId = "CARL900101AB1";
        String token = jwtUtil.generateToken(taxId);
        assertEquals(taxId, jwtUtil.extractTaxId(token));
    }
}