package com.chakray.prueba_api.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AndresFormatValidator implements ConstraintValidator<AndresFormat, String> {

	@Override
	public void initialize(AndresFormat constraintAnnotation) {
	}

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {
		if (phone == null) {
			return false;
		}
		phone = phone.trim();
		String regex = phone.replaceAll("[\\s\\-\\+]", "");
		return regex.matches("^\\d{10,13}$");
	}
}