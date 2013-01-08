package com.hermes.owasphotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
	public User getByName(String name) {
		return userDao.getByName(name);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public List<String> getNames(String prefix) {
		if (prefix == null)
			prefix = "";
		ArrayList<String> names = new ArrayList<String>();
		for (User u : userDao.findAll()) {
			String n = u.getName();
			if (n.startsWith(prefix)) {
				names.add(n);
			}
		}
		return names;
	}

	@Override
	public void save(User u) {
		userDao.save(u);
	}

	@Override
	public User update(User user) {
		return userDao.merge(user);
	}

	@Override
	public User enableUser(Integer id) {
		User u = userDao.getById(id);
		u.enable();
		return u;
	}
	
	@Override
	public User disableUser(Integer id) {
		User u = userDao.getById(id);
		u.disable();
		return u;
	}
}
