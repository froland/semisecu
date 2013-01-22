package com.hermes.owasphotel.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Checks that field values are equal.
 * <p>For example, when fields ["a", "b"] are given the values
 * of object.getA() and object.getB() must be equal.</p>
 */
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SameValueValidator.class)
public @interface SameValue {
	/**
	 * The field names to check.
	 * @return An array of names
	 */
	public String[] fieldNames();

	/**
	 * Indicates whether to ignore checking null values.
	 * @return Ignore <code>null</code>
	 */
	public boolean ignoreNull() default false;

	public String message() default "Fields do not have the same value";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
