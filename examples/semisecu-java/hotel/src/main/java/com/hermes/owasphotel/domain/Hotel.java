package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hermes.owasphotel.dao.jpa.IdentifiableEntity;

@Entity
@Table(name = "HOTEL")
@SequenceGenerator(name = "id_seq", sequenceName = "HOTELS_SEQ")
public class Hotel extends IdentifiableEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Column(name = "name", nullable = false)
	private String hotelName;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	private String address;
	private String city;
	private String country;

	private String telephone;
	private String email;
	@Column(name = "description_html")
	private String descriptionHTML;

	private Integer stars;

	private int approved;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User manager;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hotel")
	@OrderBy("sequence")
	private List<Comment> comments = new ArrayList<Comment>();

	@Transient
	private Double averageNote;

	Hotel() {
	}

	public Hotel(String name, User manager) {
		setHotelName(name);
		this.manager = manager;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		if (hotelName == null || hotelName.isEmpty())
			throw new IllegalArgumentException("Empty name");
		this.hotelName = hotelName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDescriptionHTML() {
		return descriptionHTML;
	}

	public void setDescriptionHTML(String descriptionHTML) {
		this.descriptionHTML = descriptionHTML;
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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public int getNbComments(boolean countDeleted) {
		if (countDeleted) {
			return comments.size();
		} else {
			int total = 0;
			for (Comment c : comments) {
				if (!c.isDeleted())
					total++;
			}
			return total;
		}
	}

	public Comment addComment(User user) {
		return new Comment(this, user);
	}

	public Double getAverageNote() {
		return averageNote;
	}

	public void setAverageNote(Double averageNote) {
		this.averageNote = averageNote;
	}
}
