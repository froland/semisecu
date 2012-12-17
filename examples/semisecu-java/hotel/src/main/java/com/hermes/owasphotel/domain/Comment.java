package com.hermes.owasphotel.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(CommentID.class)
@Table(name = "comments")
public class Comment {

	@Id
	@ManyToOne
	@JoinColumn(name = "hotelid")
	private Hotel hotel;
	@Id
	@Column(name = "seq")
	private int sequence;

	@Column(name = "text")
	private String text;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid")
	private User user;
	
	private int deleted;

	Comment() {
	}

	public Comment(Hotel hotel, User user) {
		if (hotel == null)
			throw new IllegalArgumentException("hotel is null");
		if (user == null)
			throw new IllegalArgumentException("user is null");
		this.hotel = hotel;
		this.user = user;
		this.sequence = hotel.getComments().size() + 1;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public int getSequence() {
		return sequence;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}
	
	public boolean isDeleted() {
		return deleted != 0;
	}

	public void deleteComment() {
		this.deleted = 1;
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