package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;

@Entity
@Table(name = "HOTEL")
@SequenceGenerator(name = "id_seq", sequenceName = "HOTELS_SEQ")
public class Hotel extends IdentifiableEntity<Integer> implements Noted {
	private static final long serialVersionUID = 1L;
	private static final Float MAX_NOTE = 5.0f;

	@Column(name = "name", nullable = false)
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Embedded
	private Address address;

	private String telephone;
	private String email;
	@Column(name = "description_html")
	private String descriptionHTML;

	private Integer stars;

	@Column(name = "approved", columnDefinition = "tinyint default false")
	private boolean approved = false;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User manager;

	// XXX eager loading, loads the comments twice
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id", nullable = false)
	@OrderBy("id")
	private List<Comment> comments = new ArrayList<Comment>();

	Hotel() {
	}

	public Hotel(String name, User manager) {
		setName(name);
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	public void setName(String hotelName) {
		if (hotelName == null || hotelName.isEmpty())
			throw new IllegalArgumentException("Empty name");
		this.name = hotelName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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
		if (stars != null && (stars > MAX_NOTE || stars.intValue() < 0)) {
			throw new IllegalArgumentException(
					"The number of stars must be between 0 and "
							+ MAX_NOTE.intValue());
		}
		this.stars = stars;
	}

	public boolean isApproved() {
		return approved;
	}

	public void approveHotel() {
		this.approved = true;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
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

	public Comment createComment(User user, int note, String text) {
		Comment c = new Comment(user);
		c.setNote(note);
		c.setText(text);
		comments.add(c);
		return c;
	}

	public float getAverageNote() {
		float note = 0.0f;
		int nbComment = 0;
		for (Comment c : getComments()) {
			if (!c.isDeleted()) {
				note += c.getNote();
				nbComment++;
			}
		}
		if (nbComment == 0) // avoid returning NaN
			return 0.0f;
		return note / nbComment;
	}

	@Override
	public Float getNoteValue() {
		return new Float(getAverageNote());
	}

	@Override
	public Float getMaxNote() {
		return MAX_NOTE;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Hotel))
			return false;
		Hotel other = (Hotel) obj;

		return ObjectUtils.equals(getName(), other.getName())
				&& ObjectUtils.equals(getAddress(), other.getAddress());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + ObjectUtils.hashCode(getName());
	}
}
