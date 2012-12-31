package com.hermes.owasphotel.service;

import java.util.List;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.dto.UserDto;

public interface UserService {

	public User find(Integer id);

	public User find(String name);

	public List<User> findAll();

	public User save(User u);

	public User update(UserDto dto, boolean asAdmin);

	public User enableUser(Integer id, boolean enable);

	public List<String> getNames(String prefix);
}
