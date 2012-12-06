package com.hermes.owasphotel.web.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resource not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Class<?> type;
	private Long id;

	/**
	 * Initializes this exception.
	 * 
	 * @param type
	 *            The type
	 * @param id
	 *            The id
	 */
	public ResourceNotFoundException(Class<?> type, Long id) {
		this.type = type;
		this.id = id;
	}

	/**
	 * Gets the id.
	 * 
	 * @return The id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the type.
	 * 
	 * @return The type of the resource
	 */
	public Class<?> getType() {
		return type;
	}
}
