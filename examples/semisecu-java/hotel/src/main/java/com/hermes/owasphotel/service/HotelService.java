package com.hermes.owasphotel.service;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;

/**
 * Service: Hotel
 * 
 * @author k
 */
public interface HotelService extends SimpleDao<Long, Hotel> {

	public void addComment(Long hotelId, String name, String text);

}
