package com.hermes.owasphotel.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameValueValidator implements
		ConstraintValidator<SameValue, Object> {

	private SameValue sv;

	@Override
	public void initialize(SameValue sv) {
		this.sv = sv;
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object expected = null;
		boolean init = true;
		for (String field : sv.fieldNames()) {
			Object fieldValue;
			try {
				fieldValue = value.getClass().getMethod(makeMethodName(field))
						.invoke(value);
			} catch (Exception e) {
				// failed to get the field value
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(
						"Invalid constraint argument on object " + value + ":"
								+ e.toString()).addConstraintViolation();
				return false;
			}
			if (fieldValue == null && sv.ignoreNull())
				continue;
			if (init) {
				expected = fieldValue;
				init = false;
			} else if (expected != fieldValue
					&& (expected != null && !expected.equals(fieldValue))) {
				return false;
			}
		}
		return true;
	}

	private String makeMethodName(String field) {
		return "get" + Character.toUpperCase(field.charAt(0))
				+ field.substring(1);
	}

}
