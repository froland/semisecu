package com.hermes.owasphotel.domain;

public class HotelListItem {

	private int id;
	private String name;
	private int nbComments;
	private float note;

	public HotelListItem(Hotel h) {
		this(h.getId(), h.getName(), h.getNbComments(false), h.getAverageNote());
	}

	public HotelListItem(int id, String hotelName, int nbComments, float note) {
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

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

}
