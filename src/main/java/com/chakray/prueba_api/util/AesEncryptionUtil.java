package com.chakray.prueba_api.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.impl.lang.Bytes;

@Component
public class AesEncryptionUtil {
	@Value("${AES_SECRET}")
	private String aesSecret;

	private final SecureRandom secureRandom = new SecureRandom();

	public String encrypt(String value) throws Exception {
		byte[] iv = new byte[16];
		secureRandom.nextBytes(iv);

		SecretKeySpec keySpec = new SecretKeySpec(aesSecret.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));

		byte[] combined = new byte[iv.length + encrypted.length];
		System.arraycopy(iv, 0, combined, 0, iv.length);
		System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

		return Base64.getEncoder().encodeToString(combined);
	}

	public String decrypt(String encryptedValue) throws Exception {
		byte[] combined = Base64.getDecoder().decode(encryptedValue);

		byte[] iv = Arrays.copyOfRange(combined, 0, 16);

		byte[] encrypted = Arrays.copyOfRange(combined, 16, combined.length);

		SecretKeySpec keySpec = new SecretKeySpec(aesSecret.getBytes(StandardCharsets.UTF_8), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(iv);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
	}

}
