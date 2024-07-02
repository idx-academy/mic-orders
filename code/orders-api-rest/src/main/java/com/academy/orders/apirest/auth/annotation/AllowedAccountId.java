package com.academy.orders.apirest.auth.annotation;

import com.academy.orders.apirest.auth.validator.AccountIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {AccountIdValidator.class})
public @interface AllowedAccountId {
	String message() default "You do not have the necessary permissions to enter this ID.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] values() default {};
}
