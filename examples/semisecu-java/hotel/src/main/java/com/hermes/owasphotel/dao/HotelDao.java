package com.hermes.owasphotel.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: {@link Hotel}
 */
public interface HotelDao extends SimpleDao<Integer, Hotel> {
	/**
	 * Finds approved hotels.
	 * @param approved True to get the approved hotels, false to get not approved hotels
	 * @return The list of approved/not approved hotel depending on param approved
	 */
	public List<Hotel> findApprovedHotels(boolean approved);

	/**
	 * Finds a hotel by name.
	 * @param search The name of a hotel
	 * @return The hotel
	 * @throws NoResultException If no hotel with that name exists
	 * @throws NonUniqueResultException When there is more than one hotel with that name
	 */
	public Hotel getByName(String search) throws NoResultException,
			NonUniqueResultException;

	/**
	 * Searchs hotels based on their names.
	 * 
	 * @param search A part of the name of the searched hotels 
	 * @param fullSearch Whether search param is the beginning or a random part of the hotel name
	 * @param maxResults Max number of results
	 * @return A list with the result of the search
	 */
	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults);

	/**
	 * Finds the hotels managed by a given user
	 * 
	 * @param user The manager of the hotels
	 * @return The list of hotel managed by user
	 */
	public List<Hotel> findManagedHotels(User user);

	/**
	 * Deletes a comment.
	 * @param commentId The id of the comment
	 */
	public void deleteComment(Integer commentId);

}
