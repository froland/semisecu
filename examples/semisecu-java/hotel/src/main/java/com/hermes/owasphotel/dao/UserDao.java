package com.hermes.owasphotel.dao;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: User
 * 
 */
public interface UserDao extends SimpleDao<Integer, User> {

	/**
	 * 
	 * Get a user by its name
	 * 
	 * @param name The name of the user
	 * @return The user if it exists null otherwise
	 */
	public User getByName(String name);
}
