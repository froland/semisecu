package com.hermes.owasphotel.service.dto;

import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

public class HotelDto extends GenericDto<Integer, Hotel> {
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

	private String manager;

	private String descriptionHTML;

	private CommonsMultipartFile file;
	private boolean deleteFile;

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

	@Override
	public void update(Hotel h) {
		super.update(h);
		if (deleteFile) {
			h.setImage(null);
		} else if (file != null && file.getSize() > 0) {
			h.setImage(file.getBytes());
		}
		// manager is not updated directly from this DTO
	}

	@Override
	public void read(Hotel domain) {
		super.read(domain);
		manager = domain.getManager().getName();
	}

	@Override
	protected Set<String> ignoredFields(boolean update) {
		Set<String> s = super.ignoredFields(update);
		if (update) {
			s.add("file");
			s.add("deleteFile");
		}
		s.add("manager");
		return s;
	}

	public Hotel makeNew(User user) {
		Hotel h = new Hotel(hotelName, user);
		update(h);
		return h;
	}
}
