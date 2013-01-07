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
		return userDao.getById(id);
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
	public void save(User u) {
		userDao.save(u);
	}

	@Override
	public User update(UserDto dto, boolean asAdmin) {
		User u = userDao.getById(dto.getId());
		if (u == null)
			return null;
		String oldName = u.getName();
		dto.updatePassword(u, asAdmin);
		dto.update(u);
		if (!asAdmin && !oldName.equals(u.getName())) {
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
		User u = userDao.getById(id);
		u.setEnabled(enable);
		return u;
	}
}
