package com.hermes.owasphotel.web.mvc.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;

public class HotelForm {
	private Integer id;

	@NotBlank(message = "The name of the hotel may not be blank")
	private String hotelName;

	private String address;
	private String city;
	private String country;

	private String telephone;
	@Email
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

	public HotelForm(Hotel hotel) {
		id = hotel.getId();
		hotelName = hotel.getName();

		address = hotel.getAddress();
		city = hotel.getCity();
		country = hotel.getCountry();

		telephone = hotel.getTelephone();
		email = hotel.getEmail();
		stars = hotel.getStars();
		descriptionHTML = hotel.getDescriptionHTML();

		manager = hotel.getManager().getName();
	}

	public void update(Hotel h, UserService userService) {
		h.setName(hotelName);
		h.setAddress(address);
		h.setCity(city);
		h.setCountry(country);
		h.setTelephone(telephone);
		h.setEmail(email);
		h.setStars(stars);
		h.setDescriptionHTML(descriptionHTML);

		if (userService != null) {
			h.setManager(userService.getByName(this.manager));
		}

		if (deleteFile) {
			h.setImage(null);
		} else if (file != null && file.getSize() > 0) {
			h.setImage(file.getBytes());
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
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

	public Hotel makeNew(User user) {
		Hotel h = new Hotel(hotelName, user);
		update(h, null);
		return h;
	}
}
