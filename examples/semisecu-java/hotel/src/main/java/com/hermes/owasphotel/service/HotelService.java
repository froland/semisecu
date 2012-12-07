package com.hermes.owasphotel.service;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService extends SimpleDao<Integer, Hotel> {

	public void addComment(Integer hotelId, String name, String text);

}
