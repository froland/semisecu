package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService extends SimpleDao<Integer, Hotel> {

	public List<Hotel> findApproved();

	public void approve(Hotel h);

	public void addComment(Integer hotelId, String name, String text);

}
