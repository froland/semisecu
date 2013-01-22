package com.hermes.owasphotel.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A comment posted for a hotel.
 */
@Entity
@Table(name = "COMMENT")
@SequenceGenerator(name = "id_seq", sequenceName = "COMMENT_SEQ")
public class Comment extends IdentifiableEntity<Integer> implements Noted {
	private static final long serialVersionUID = 1L;

	/**
	 * The maximum note.
	 */
	public static final int MAX_NOTE = 5;

	@Column(name = "when")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "note")
	private int note;
	@Column(name = "text")
	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@Column(name = "deleted", columnDefinition = "tinyint default false")
	private boolean deleted = false;

	Comment() {
	}

	/**
	 * Initializes this comment.
	 * @param user The user
	 */
	Comment(User user) {
		this.user = user;
		this.date = new Date();
	}

	public int getNote() {
		return note;
	}

	/**
	 * Sets a note.
	 * @param note The new note
	 * @throws IllegalArgumentException when note is zero, negative of greater
	 *         than {@link #MAX_NOTE}
	 */
	public void setNote(int note) {
		if (note < 1 || note > MAX_NOTE)
			throw new IllegalArgumentException("Invalid note: " + note);
		this.note = note;
	}

	public String getText() {
		return text;
	}

	/**
	 * Sets the text of the comment.
	 * @param text The text
	 * @throws IllegalArgumentException when text is null
	 */
	public void setText(String text) {
		if (text == null)
			throw new IllegalArgumentException(
					"The text of the comment is null");
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	/**
	 * Gets the user's name.
	 * @return The user's name or anonymous string when there is no user
	 */
	public String getUserName() {
		if (user != null)
			return user.getName();
		return "Anonymous";
	}

	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Marks this comment as deleted.
	 */
	public void delete() {
		this.deleted = true;
	}

	@Override
	public Float getNoteValue() {
		return new Float(getNote());
	}

	@Override
	public Float getMaxNote() {
		return (float) MAX_NOTE;
	}
}
