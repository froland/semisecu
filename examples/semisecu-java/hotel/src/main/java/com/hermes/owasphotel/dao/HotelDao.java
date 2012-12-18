package com.hermes.owasphotel.dao;

import java.util.List;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;

/**
 * DAO: Hotel
 * 
 * @author k
 */
public interface HotelDao extends SimpleDao<Integer, Hotel> {
	public List<Hotel> findApprovedHotels();

	public void computeNote(Hotel h);

	public List<Hotel> findTopNotedHotels(int count);

	public Hotel findByName(String search);

	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults);

}
