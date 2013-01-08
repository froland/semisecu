package com.hermes.owasphotel.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@IdClass(CommentID.class)
@Table(name = "comment")
public class Comment {

	@Id
	@ManyToOne
	@JoinColumn(name = "hotel_id", nullable = false)
	private Hotel hotel;
	@Id
	@Column(name = "seq")
	private int sequence;

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
	
	
	@Column(name="deleted", columnDefinition = "tinyint default false")
	private boolean deleted = false;

	Comment() {
	}

	Comment(Hotel hotel, User user) {
		if (hotel == null)
			throw new IllegalArgumentException("hotel is null");
		this.hotel = hotel;
		this.user = user;
		this.date = new Date();

		// add to the comment list
		List<Comment> comments = hotel.getComments();
		this.sequence = comments.size() + 1;
		comments.add(this);
	}

	public Hotel getHotel() {
		return hotel;
	}

	public int getSequence() {
		return sequence;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
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
}

class CommentID implements Serializable {
	private static final long serialVersionUID = 1L;

	public Integer hotel;
	public int sequence;

	@Override
	public boolean equals(Object obj) {
		CommentID c = obj instanceof CommentID ? (CommentID) obj : null;
		return c != null
				&& (c.hotel == hotel || (hotel != null && hotel.equals(c.hotel)))
				&& c.sequence == sequence;
	}

	@Override
	public int hashCode() {
		return hotel.hashCode() + sequence;
	}
}