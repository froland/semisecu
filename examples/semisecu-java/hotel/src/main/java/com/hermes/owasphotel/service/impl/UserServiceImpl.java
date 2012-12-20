package com.hermes.owasphotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.service.dto.UserDto;

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
		String oldName = u.getName();
		dto.update(u);
		if (!oldName.equals(u.getName())) {
			// re-authentify the user
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			ctx.setAuthentication(new UsernamePasswordAuthenticationToken(dto
					.getName(), auth.getCredentials(), auth.getAuthorities()));
		}
		return u;
	}

	@Override
	public User enableUser(Integer id, boolean enable) {
		User u = userDao.find(id);
		u.setEnabled(enable);
		return u;
	}
}
