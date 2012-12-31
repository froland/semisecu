package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.service.dto.HotelDto;
import com.hermes.owasphotel.service.dto.HotelListItemDto;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService {

	public Hotel find(Integer id);

	public Hotel save(Hotel h);

	public void delete(Hotel h);

	public Hotel findByName(String search);

	public List<String> findForAutoComplete(String query);

	public Hotel approve(Integer hotelId);

	public void addComment(Integer hotelId, String name,
			boolean authentifiedUser, int note, String text);

	public void deleteComment(Integer hotelId, int commentSeq);

	public Hotel update(Integer hotelId, HotelDto data);

	public byte[] getHotelImage(Integer hotelId);

	public void setHotelImage(Integer hotelId, byte[] image);

	public List<HotelListItemDto> listAll();

	public List<HotelListItemDto> listTopNoted(int count);

	public List<HotelListItemDto> listApproved();

	public List<HotelListItemDto> listNotApproved();

	public List<HotelListItemDto> listSearchQuery(String search);

	public List<HotelListItemDto> listManagedHotels(String name);
}
