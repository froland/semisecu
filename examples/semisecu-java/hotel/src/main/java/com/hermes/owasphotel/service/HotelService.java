package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.HotelListItem;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService {

	public Hotel find(Integer id);

	public void save(Hotel h);

	public void delete(Hotel h);

	public Hotel findByName(String search);

	public List<String> findForAutoComplete(String query);

	public Hotel approve(Integer hotelId);

	public void addComment(Integer hotelId, String name,
			boolean authentifiedUser, int note, String text);

	public void deleteComment(Integer hotelId, int commentSeq);

	public Hotel update(Hotel hotel);

	public byte[] getHotelImage(Integer hotelId);

	public void setHotelImage(Integer hotelId, byte[] image);

	public List<HotelListItem> listAll();

	public List<HotelListItem> listTopNoted(int count);

	public List<HotelListItem> listApproved();

	public List<HotelListItem> listNotApproved();

	public List<HotelListItem> listSearchQuery(String search);

	public List<HotelListItem> listManagedHotels(String name);
}
