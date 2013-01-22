package com.hermes.owasphotel.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.hermes.owasphotel.domain.Hotel;

/**
 * Service: {@link Hotel}
 */
public interface HotelService {

	/**
	 * Gets a hotel by ID.
	 * @param id The hotel's id
	 * @return The hotel
	 * @throws NoResultException when no matching hotel was found
	 */
	public Hotel getById(Integer id);

	/**
	 * Saves a hotel.
	 * @param h The hotel
	 */
	public void save(Hotel h);

	/**
	 * Deletes a hotel.
	 * @param h The hotel to delete
	 */
	public void delete(Hotel h);

	/**
	 * Finds a hotel by its name.
	 * 
	 * @param search The name of the hotel to search for.
	 * @return The hotel with a matching name
	 * @throws NoResultException when no hotel with that name exists
	 * @throws NonUniqueResultException when multiple hotels with that name exist
	 */
	public Hotel getByName(String search) throws NoResultException;

	/**
	 * Finds the hotel names beginning with the given query.
	 * 
	 * @param query The beginning of an hotel name
	 * @return The list of the matching names
	 */
	public List<String> findForAutoComplete(String query);

	/**
	 * Approves a hotel.
	 * 
	 * @param hotelId The id of the hotel to approve
	 * @return The approved hotel
	 */
	public Hotel approve(Integer hotelId);

	/**
	 * Adds a new comment to the hotel.
	 * 
	 * @param hotelId The id of the commented hotel
	 * @param name The username of the commentatot
	 * @param note The given note
	 * @param text The text of the comment
	 * @see Hotel#createComment(com.hermes.owasphotel.domain.User, int, String)
	 */
	public void addComment(Integer hotelId, String name, int note, String text);

	/**
	 * Marks a comment as deleted.
	 * 
	 * @param commentId the id of the comment
	 */
	public void deleteComment(Integer commentId);

	/**
	 * Updates the hotel
	 * 
	 * @param hotel
	 * @return the updated hotel
	 */
	public Hotel update(Hotel hotel);

	/**
	 * Gets the hotel image.
	 * @param hotelId The hotel id
	 * @return The image or <code>null</code>
	 */
	public byte[] getHotelImage(Integer hotelId);

	/**
	 * Sets the hotel image.
	 * @param hotelId The hotel id
	 * @param image The image, may be <code>null</code> to remove the existing one
	 */
	public void setHotelImage(Integer hotelId, byte[] image);

	/**
	 * Lists all hotels.
	 * 
	 * @return A list with all hotels
	 */
	public List<Hotel> listAll();

	/**
	 * Lists the top noted hotels
	 * 
	 * @param count The number of hotels to find
	 * @return The list of top noted hotel in descending order
	 */
	public List<Hotel> listTopNoted(int count);

	/**
	 * Lists the approved hotel.
	 * 
	 * @return A list with the approved hotels
	 */
	public List<Hotel> listApproved();

	/**
	 * Lists the hotels that aren't approved.
	 * 
	 * @return The list of not approved hotels
	 */
	public List<Hotel> listNotApproved();

	/**
	 * Lists the hotels whose name contain the search query.
	 * 
	 * @param search The name part to match
	 * @return A list of the matching hotels
	 */
	public List<Hotel> listSearchQuery(String search);

	/**
	 * Lists the hotels managed by a user.
	 * 
	 * @param userName The name of the manager
	 * @return A list of the hotels manage by the user
	 */
	public List<Hotel> listManagedHotels(String userName);
}
