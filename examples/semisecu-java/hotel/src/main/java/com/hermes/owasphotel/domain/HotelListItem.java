package com.hermes.owasphotel.domain;

public class HotelListItem {

	private int id;
	private String hotelName;
	private int nbComments;
	private Double note;

	public HotelListItem(int id, String hotelName, int nbComments, Double note) {
		this.id = id;
		this.hotelName = hotelName;
		this.nbComments = nbComments;
		this.note = note;
	}

	public int getId() {
		return id;
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
