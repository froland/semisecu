package com.hermes.owasphotel.service;

import java.util.List;

import javax.persistence.NoResultException;

import com.hermes.owasphotel.domain.User;

/**
 * Service: {@link User}
 */
public interface UserService {

	/**
	 * Gets the user by ID.
	 * @param id The id
	 * @return The user
	 * @throws NoResultException when no user matches
	 */
	public User getById(Integer id);

	/**
	 * Gets the user by their name.
	 * @param name The name
	 * @return The user
	 * @throws NoResultException when no user matches
	 * @throws NonUniqueResultException If more than one user matches
	 */
	public User getByName(String name);

	/**
	 * Finds all the users.
	 * @return The list of users
	 */
	public List<User> findAll();

	/**
	 * Saves the user.
	 * @param u The user
	 */
	public void save(User u);

	/**
	 * Updates the user.
	 * @param u The user
	 * @return
	 */
	public User update(User u);

	/**
	 * Gets the name of the users whose name begins with a given prefix.
	 * 
	 * @param prefix A prefix for user names
	 * @return A list with the matching userNames
	 */
	public List<String> getNames(String prefix);

	/**
	 * Disables a user.
	 * @param id The user's id
	 */
	public void disableUser(Integer id);

	/**
	 * Enables a user.
	 * @param id The user's id
	 */
	public void enableUser(Integer id);
}
