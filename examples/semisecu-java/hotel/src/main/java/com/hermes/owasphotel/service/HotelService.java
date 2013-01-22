package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.Hotel;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService {

	public Hotel getById(Integer id);

	public void save(Hotel h);

	public void delete(Hotel h);

	public Hotel getByName(String search);

	public List<String> findForAutoComplete(String query);

	public Hotel approve(Integer hotelId);

	public void addComment(Integer hotelId, String name, int note, String text);

	public void deleteComment(Integer commentId);

	public Hotel update(Hotel hotel);

	public byte[] getHotelImage(Integer hotelId);

	public void setHotelImage(Integer hotelId, byte[] image);

	public List<Hotel> listAll();

	public List<Hotel> listTopNoted(int count);

	public List<Hotel> listApproved();

	public List<Hotel> listNotApproved();

	public List<Hotel> listSearchQuery(String search);

	public List<Hotel> listManagedHotels(String userName);
}
