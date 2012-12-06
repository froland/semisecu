package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hermes.owasphotel.dao.jpa.IdentifiableEntity;

@Entity
@Table(name = "HOTELS")
public class Hotel extends IdentifiableEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Column(name = "hotelname", unique = true)
	private String hotelName;

	private String address;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "hotelid")
	private List<Comment> comments = new ArrayList<Comment>();

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Comment> getComments() {
		return comments;
	}
}
