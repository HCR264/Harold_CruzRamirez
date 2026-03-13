package com.chakray.prueba_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "AES_SECRET=B7eJlHgOjkcduAyQf0RMv4bSCiZWK6mG",
    "JWT_SECRET=F+qZMXg76fufJNpo8PR779brM84QpO8NpPyunvWap58=",
    "JWT_EXPIRATION=86400000"
})
class PruebaApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
