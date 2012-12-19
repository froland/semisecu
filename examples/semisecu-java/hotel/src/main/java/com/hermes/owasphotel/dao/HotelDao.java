package com.hermes.owasphotel.dao;

import java.util.List;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: Hotel
 * 
 * @author k
 */
public interface HotelDao extends SimpleDao<Integer, Hotel> {
	public List<Hotel> findApprovedHotels(boolean approved);

	public void computeNote(Hotel h);

	public List<Hotel> findTopNotedHotels(int count);

	public Long countComments(Hotel h);

	public Hotel findByName(String search);

	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults);

	public List<Hotel> findManagedHotels(User user);

}
