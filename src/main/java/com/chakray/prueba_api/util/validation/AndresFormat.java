package com.chakray.prueba_api.util.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = AndresFormatValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AndresFormat {
	String message() default "Invalid phone format. Must be 10 digits optionally with country code starting with +";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}