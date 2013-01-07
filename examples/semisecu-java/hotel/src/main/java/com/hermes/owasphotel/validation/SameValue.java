package com.hermes.owasphotel.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SameValueValidator.class)
public @interface SameValue {
	public String[] fieldNames();

	public boolean ignoreNull() default false;

	public String message() default "Fields do not have the same value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
