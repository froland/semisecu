package com.hermes.owasphotel.web.mvc.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.bval.constraints.Email;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hermes.owasphotel.domain.Address;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.validation.NotBlank;

/**
 * Hotel submission form.
 */
public class HotelForm {
	private Integer id;

	@NotBlank(message = "The name of the hotel may not be blank")
	private String name;

	private String street;
	private String number;
	private String city;
	private String zipCode;
	private String country;

	private String telephone;
	@Email(message = "Invalid e-mail address")
	private String email;
	@Min(0)
	@Max(5)
	@NotNull
	private Integer stars;
	private String descriptionHTML;

	private String manager;

	private CommonsMultipartFile file;
	private boolean deleteFile;

	public HotelForm() {
		// default constructor
	}

	/**
	 * Initializes this form.
	 * @param hotel The hotel
	 */
	public HotelForm(Hotel hotel) {
		if (hotel == null)
			throw new IllegalArgumentException("hotel is null");
		id = hotel.getId();
		name = hotel.getName();
		Address address = hotel.getAddress();
		if (address != null) {
			street = address.getStreet();
			number = address.getNumber();
			zipCode = address.getZipCode();
			city = address.getCity();
			country = address.getCountry();
		}

		telephone = hotel.getTelephone();
		email = hotel.getEmail();
		stars = hotel.getStars();
		descriptionHTML = hotel.getDescriptionHTML();

		manager = hotel.getManager().getName();
	}

	/**
	 * Updates a hotel using this form.
	 * @param h The hotel to update
	 * @param userService The user service (optional)
	 */
	public void update(Hotel h, UserService userService) {
		if (h == null)
			throw new IllegalArgumentException("no hotel to update given");
		h.setName(name);
		h.setAddress(new Address(street, number, city, zipCode, country));
		h.setTelephone(telephone);
		h.setEmail(email);
		h.setStars(stars);
		h.setDescriptionHTML(descriptionHTML);

		if (userService != null && this.manager != null) {
			User manager = userService.getByName(this.manager);
			if (manager == null)
				throw new IllegalArgumentException("Manager user ("
						+ this.manager + ") does not exist");
			h.setManager(manager);
		}

		if (deleteFile) {
			h.setImage(null);
		} else if (file != null && file.getSize() > 0) {
			h.setImage(file.getBytes());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setEmail(String email) {
		this.email = email;
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
		this.stars = stars;
	}

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}

	public boolean isDeleteFile() {
		return deleteFile;
	}

	public void setDeleteFile(boolean deleteFile) {
		this.deleteFile = deleteFile;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * Creates a new hotel using this form.
	 * @param user The manager of the hotel
	 * @return A new hotel
	 */
	public Hotel makeNew(User user) {
		Hotel h = new Hotel(name, user);
		update(h, null);
		return h;
	}
}
