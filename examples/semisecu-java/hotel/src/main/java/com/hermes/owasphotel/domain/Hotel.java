package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hermes.owasphotel.dao.jpa.IdentifiableEntity;

@Entity
@Table(name = "HOTELS")
@SequenceGenerator(name = "id_seq", sequenceName = "HOTELS_SEQ")
@NamedQueries({ @NamedQuery(name = "Hotel.findApproved", query = "from Hotel where approved != 0") })
public class Hotel extends IdentifiableEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Column(name = "hotelname")
	private String hotelName;

	private String address;
	private String city;
	private String country;

	private String telephone;
	private String email;

	private Integer stars;

	private int approved;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return telephone;
	}

	public void setPhone(String phone) {
		this.telephone = phone;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		if (stars != null && (stars.intValue() > 5 || stars.intValue() < 0)) {
			throw new IllegalArgumentException(
					"The number of stars must be between 0 and 5");
		}
		this.stars = stars;
	}

	public boolean isApproved() {
		return approved != 0;
	}

	public void approveHotel() {
		this.approved = 1;
	}

	public List<Comment> getComments() {
		return comments;
	}
}
