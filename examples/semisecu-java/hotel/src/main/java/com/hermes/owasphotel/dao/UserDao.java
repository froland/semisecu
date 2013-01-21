package com.hermes.owasphotel.dao;

import javax.persistence.NoResultException;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: User
 * 
 * @author k
 */
public interface UserDao extends SimpleDao<Integer, User> {
	public User getByName(String name) throws NoResultException;
}
