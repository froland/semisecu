package com.hermes.owasphotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserDto;
import com.hermes.owasphotel.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User find(Integer id) {
		return userDao.find(id);
	}

	@Override
	public User find(String name) {
		return userDao.find(name);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public boolean isUsed(String name) {
		return find(name) != null;
	}

	@Override
	public User save(User u) {
		return userDao.save(u);
	}

	@Override
	public User update(UserDto dto) {
		User u = userDao.find(dto.getId());
		dto.update(u);
		return u;
	}

	@Override
	public User enableUser(Integer id, boolean enable) {
		User u = userDao.find(id);
		u.setEnabled(enable);
		return u;
	}
}
