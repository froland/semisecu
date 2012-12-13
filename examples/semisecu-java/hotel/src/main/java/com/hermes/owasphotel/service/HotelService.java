package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.HotelNote;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService {

	public Hotel find(Integer id);

	public Hotel save(Hotel h);

	public void delete(Hotel h);

	public List<Hotel> findAll();

	public List<Hotel> findApproved();

	public List<Hotel> findTopNoted(int count);

	public void approve(Hotel h);

	public void addComment(Integer hotelId, String name, String text);

	public HotelNote getHotelNote(Integer hotelId, String name);

	public void setHotelNote(Integer hotelId, String name, int note);

	public Hotel update(Integer hotelId, HotelDto data);
}
