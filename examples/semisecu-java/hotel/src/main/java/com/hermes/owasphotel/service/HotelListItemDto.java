package com.hermes.owasphotel.service;

import com.hermes.owasphotel.domain.Hotel;

public class HotelListItemDto extends GenericDto<Integer, Hotel> {
	
	private String hotelName;
	
	private Long nbComments;
	
	private Double note;
	

	public HotelListItemDto(String hotelName, Long nbComments, Double note, Integer id) {
		super();
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

	public Long getNbComments() {
		return nbComments;
	}

	public void setNbComments(Long nbComments) {
		this.nbComments = nbComments;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}
	
}
