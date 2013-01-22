package com.hermes.owasphotel.service;

import java.util.List;

import javax.persistence.NoResultException;

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

	/**
	 * 
	 * Finds a hotel by its name
	 * 
	 * @param search The name of the hotel to search for.
	 * @return The Hotel that was found and null if more than one results were found
	 * @throws NoResultException If no results were found
	 */
	public Hotel getByName(String search) throws NoResultException;

	/**
	 * 
	 * Finds the hotels name beginning with param query
	 * 
	 * @param query The beginning of an hotel name
	 * @return The list of the matching name
	 */
	public List<String> findForAutoComplete(String query);

	/**
	 * 
	 * Approves an hotel.
	 * 
	 * @param hotelId The id of the hotel to approve
	 * @return The approved hotel
	 */
	public Hotel approve(Integer hotelId);

	/**
	 * 
	 * Adds a new comment to the hotel
	 * 
	 * @param hotelId The id of the commented hotel
	 * @param name The username of the commentatot
	 * @param note The given note
	 * @param text The text of the comment
	 */
	public void addComment(Integer hotelId, String name, int note, String text);

	/**
	 * 
	 * Marks a comment as deleted
	 * 
	 * @param commentId the id of the comment
	 */
	public void deleteComment(Integer commentId);

	/**
	 * 
	 * Update the hotel
	 * 
	 * @param hotel
	 * @return the updated hotel
	 */
	public Hotel update(Hotel hotel);

	public byte[] getHotelImage(Integer hotelId);

	public void setHotelImage(Integer hotelId, byte[] image);

	/**
	 * 
	 * Lists all hotels
	 * 
	 * @return A list with all hotels
	 */
	public List<Hotel> listAll();

	/**
	 * Lists the count top noted hotels
	 * 
	 * @param count The number of hotels to find
	 * @return The list of top noted hotel in descending order
	 */
	public List<Hotel> listTopNoted(int count);

	/**
	 * 
	 * Lists the approved hotel
	 * 
	 * @return A list with the approved hotels
	 */
	public List<Hotel> listApproved();

	/**
	 * 
	 * Lists the hotels that aren't approved
	 * 
	 * @return The list of not approved hotels
	 */
	public List<Hotel> listNotApproved();

	/**
	 * 
	 * Lists the hotels whose name contains param search
	 * 
	 * @param search The name part to match
	 * @return A list of the matching hotels
	 */
	public List<Hotel> listSearchQuery(String search);

	/**
	 * Lists the hotels managed by a user
	 * 
	 * @param userName The name of the manager
	 * @return A list of the hotels manage by the user
	 */
	public List<Hotel> listManagedHotels(String userName);
}
