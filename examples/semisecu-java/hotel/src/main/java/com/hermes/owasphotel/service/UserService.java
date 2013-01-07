package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.User;

public interface UserService {

	public User find(Integer id);

	public User find(String name);

	public List<User> findAll();

	public void save(User u);

	public User update(User u);

	public User enableUser(Integer id, boolean enable);

	public List<String> getNames(String prefix);
}
