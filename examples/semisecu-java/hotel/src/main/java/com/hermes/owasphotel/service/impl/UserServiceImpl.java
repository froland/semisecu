package com.hermes.owasphotel.service.impl;

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

	@Override
	public User find(Integer id) {
		return userDao.find(id);
	}

	@Override
	public User find(String name) {
		return userDao.find(name);
	}
	
	@Override
	public boolean isUsed(String name)
	{
		return find(name) != null;
	}

	@Override
	public boolean isMatching(Integer id, String name) {
		if(find(id) == null)
			return false;
		return find(id).getName().equals(name);
	}
	
	@Override
	public User add(User u)
	{
		if(! isUsed(u.getName()))
			return userDao.save(u);
		else
			return null;
		//TODO replace return null by exception
	}
	
	@Override
	public User update(User u)
	{
		if(isUsed(u.getName()))
			return userDao.save(u);
		else
			return null;
		//TODO replace return null by exception
	}

}
