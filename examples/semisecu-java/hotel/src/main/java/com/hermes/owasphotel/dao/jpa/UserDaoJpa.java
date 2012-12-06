package com.hermes.owasphotel.dao.jpa;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: User
 * 
 * @author k
 */
@Repository("userDao")
public class UserDaoJpa extends SimpleJPA<Long, User> implements UserDao {
	/**
	 * Initializes this DAO.
	 */
	public UserDaoJpa() {
		super(User.class);
	}

	@Override
	public User find(String name) {
		try {
			return em.createQuery("from User where name=:name", User.class)
					.setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
