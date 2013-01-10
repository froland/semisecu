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

@Entity
@Table(name = "COMMENT")
@SequenceGenerator(name = "id_seq", sequenceName = "COMMENT_SEQ")
public class Comment extends IdentifiableEntity<Integer> implements Noted {
	private static final long serialVersionUID = 1L;
	private static final Float MAX_NOTE = 5.0f;

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

	Comment(User user) {
		this.user = user;
		this.date = new Date();
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		if (note < 1 || note > MAX_NOTE)
			throw new NullPointerException("Invalid note");
		this.note = note;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public User getUser() {
		return user;
	}

	public String getUserName() {
		if (user != null)
			return user.getName();
		return "Anonymous";
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void delete() {
		this.deleted = true;
	}

	@Override
	public Float getNoteValue() {
		return new Float(getNote());
	}

	@Override
	public Float getMaxNote() {
		return MAX_NOTE;
	}
}
