package com.hermes.owasphotel.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@IdClass(HotelNoteID.class)
@Table(name = "HOTEL_NOTES")
public class HotelNote {
	@Id
	@ManyToOne
	@JoinColumn(name = "hotelid")
	private Hotel hotel;
	@Id
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	@Column(nullable = false)
	private Integer note;

	HotelNote() {
	}

	public HotelNote(Hotel hotel, User user, int note) {
		this.hotel = hotel;
		this.user = user;
		this.note = note;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public User getUser() {
		return user;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}
}

class HotelNoteID implements Serializable {
	private static final long serialVersionUID = 1L;

	public Integer hotel;
	public Integer user;

	@Override
	public boolean equals(Object obj) {
		HotelNoteID c = obj instanceof HotelNoteID ? (HotelNoteID) obj : null;
		return c != null
				&& (c.hotel == hotel || (hotel != null && hotel.equals(c.hotel)))
				&& (c.user == user || (user != null && user.equals(c.user)));
	}

	@Override
	public int hashCode() {
		return hotel.hashCode() + user.hashCode();
	}
}