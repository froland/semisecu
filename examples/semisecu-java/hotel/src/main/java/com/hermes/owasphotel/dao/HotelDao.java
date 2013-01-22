package com.hermes.owasphotel.dao;

import java.util.List;

import javax.persistence.NoResultException;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: Hotel
 * 
 * 
 */
public interface HotelDao extends SimpleDao<Integer, Hotel> {
	/**
	 * @param approved True to get the approved hotels, false to get not approved hotels
	 * @return The list of approved/not approved hotel depending on param approved
	 */
	public List<Hotel> findApprovedHotels(boolean approved);


	/**
	 * @param search The begining of the name of an hotel
	 * @return If only one hotel matches that hotel, null otherwise
	 * @throws NoResultException If no results where found
	 */
	public Hotel getByName(String search) throws NoResultException;


	/**
	 * 
	 * Search hotels based on their names
	 * 
	 * @param search A part of the name of the searched hotels 
	 * @param fullSearch Whether search param is the beginning or a random part of the hotel name
	 * @param maxResults Max number of results
	 * @return A list with the result of the search
	 */
	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults);

	/**
	 * 
	 * Find the hotels managed by a given user
	 * 
	 * @param user The manager of the hotels
	 * @return The list of hotel managed by user
	 */
	public List<Hotel> findManagedHotels(User user);

	public void deleteComment(Integer commentId);

}
