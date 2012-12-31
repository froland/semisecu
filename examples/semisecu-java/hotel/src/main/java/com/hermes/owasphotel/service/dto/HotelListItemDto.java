package com.hermes.owasphotel.service.dto;

import com.hermes.owasphotel.domain.Hotel;

public class HotelListItemDto extends GenericDto<Integer, Hotel> {

	private String hotelName;

	private int nbComments;

	private Double note;

	public HotelListItemDto(String hotelName, int nbComments, Double note,
			Integer id) {
		this.hotelName = hotelName;
		this.nbComments = nbComments;
		this.note = note;
		this.setId(id);
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getNbComments() {
		return nbComments;
	}

	public void setNbComments(int nbComments) {
		this.nbComments = nbComments;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}

}
