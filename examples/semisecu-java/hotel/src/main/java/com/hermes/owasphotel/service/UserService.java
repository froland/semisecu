package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.User;

public interface UserService {

	public User getById(Integer id);

	public User getByName(String name);

	public List<User> findAll();

	public void save(User u);

	public User update(User u);

	/**
	 * 
	 * Gets the name of the users whose name begin with param prefix
	 * 
	 * @param prefix A prefix of usernames
	 * @return A list with the matching userNames
	 */
	public List<String> getNames(String prefix);

	public void disableUser(Integer id);

	public void enableUser(Integer id);
}
