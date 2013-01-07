package com.hermes.owasphotel.domain;

import java.io.Serializable;

/**
 * An identifiable object.
 * @author k
 *
 * @param <I> The type of the key
 */
public interface Identifiable<I extends Serializable> extends Serializable {
	/**
	 * Gets the id of the object.
	 * @return The id
	 */
	public I getId();
}
