package com.hermes.owasphotel.domain;

public class HotelListItem {

	private int id;
	private String name;
	private int nbComments;
	private Double note;

	public HotelListItem(int id, String hotelName, int nbComments, Double note) {
		this.id = id;
		this.name = hotelName;
		this.nbComments = nbComments;
		this.note = note;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String hotelName) {
		this.name = hotelName;
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
