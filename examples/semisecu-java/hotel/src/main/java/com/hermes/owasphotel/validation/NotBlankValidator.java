package com.hermes.owasphotel.validation;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that a string is not a blank.
 * <p>A blank string matches the regex <pre>^\s*$</pre>.</p>
 */
public class NotBlankValidator implements
		ConstraintValidator<NotBlank, CharSequence> {
	private static final Pattern BLANK = Pattern.compile("^\\s*$");

	@Override
	public void initialize(NotBlank constraintAnnotation) {
	}

	@Override
	public boolean isValid(CharSequence value,
			ConstraintValidatorContext context) {
		return !BLANK.matcher(value).matches();
	}

}
