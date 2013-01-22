package com.hermes.owasphotel.dao;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: {@link User}
 */
public interface UserDao extends SimpleDao<Integer, User> {

	/**
	 * Get a user by its name.
	 * 
	 * @param name The name of the user
	 * @return The user
	 * @throws NoResultException if no user matches
	 */
	public User getByName(String name) throws NoResultException, NonUniqueResultException;

}
