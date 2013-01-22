package com.hermes.owasphotel.domain;

/**
 * An interface for objects which have a note that can be represented with a bar.
 */
public interface Noted {

	/**
	 * Gets the value of the note.
	 * @return The value
	 */
	public Float getNoteValue();

	/**
	 * Gets the maximal value of the note.
	 * @return The maximal value
	 */
	public Float getMaxNote();

}
