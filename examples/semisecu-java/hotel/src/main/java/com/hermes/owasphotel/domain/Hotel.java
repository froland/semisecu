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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * A hotel.
 */
@Entity
@Table(name = "HOTEL")
@SequenceGenerator(name = "id_seq", sequenceName = "HOTELS_SEQ")
public class Hotel extends IdentifiableEntity<Integer> implements Noted {
	private static final long serialVersionUID = 1L;

	/**
	 * The maximum rating for a hotel.
	 */
	public static final int MAX_STARS = 5;

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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "hotel_id", nullable = false, unique = true)
	@OrderBy("id")
	/*
	 * Force FetchMode.SELECT to avoid duplicated comment entries. More info:
	 * https://hibernate.onjira.com/browse/HHH-6783
	 */
	@Fetch(FetchMode.SELECT)
	private List<Comment> comments = new ArrayList<Comment>();

	Hotel() {
	}

	/**
	 * Initializes this hotel.
	 * @param name The name of the hotel
	 * @param manager The manager
	 * @see #setName(String)
	 */
	public Hotel(String name, User manager) {
		setName(name);
		this.manager = manager;
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the hotel.
	 * @param hotelName The new name
	 * @throws IllegalArgumentException when the hotelName is empty
	 */
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

	/**
	 * Sets the stars (rating).
	 * @param stars The stars (may be <code>null</code>)
	 * @throws IllegalArgumentException when stars is zero, negative or
	 *         greater than {@link #MAX_STARS}
	 */
	public void setStars(Integer stars) {
		if (stars != null && (stars > MAX_STARS || stars.intValue() < 1)) {
			throw new IllegalArgumentException(
					"The number of stars must be between 0 and " + MAX_STARS);
		}
		this.stars = stars;
	}

	public boolean isApproved() {
		return approved;
	}

	/**
	 * Approves the hotel.
	 */
	public void approveHotel() {
		this.approved = true;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	/**
	 * Gets the list of comments.
	 * @return An unmodifiable list of comments
	 */
	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
	}

	/**
	 * Gets the number of comments.
	 * <p>Equivalent to:</p>
	 * <pre>getNbComments(false);</pre>
	 * @return The number of comments
	 * @see #getNbComments(boolean)
	 */
	public int getCommentCount() {
		return getNbComments(false);
	}

	/**
	 * Gets the number of comments.
	 * @param countDeleted Whether to count deleted comments
	 * @return The number of comments
	 */
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

	/**
	 * Creates a new comment.
	 * @param user The user (optional)
	 * @param note The note
	 * @param text The text
	 * @return The created comment
	 * @throws IllegalArgumentException when text is null or the note is invalid
	 */
	public Comment createComment(User user, int note, String text) {
		Comment c = new Comment(user);
		c.setNote(note);
		c.setText(text);
		comments.add(c);
		return c;
	}

	/**
	 * Gets the average note for the hotel.
	 * <p>The average is computed for non-deleted comment notes
	 * ({@link Comment#getNote()}).</p>
	 * @return The average note
	 */
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
		return (float) MAX_STARS;
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
