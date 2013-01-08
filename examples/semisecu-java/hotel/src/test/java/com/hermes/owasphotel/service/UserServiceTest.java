package com.hermes.owasphotel.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.impl.UserServiceImpl;

public class UserServiceTest {
	private UserService userService;
	private UserDao userDao;

	@Before
	public void initService() {
		UserServiceImpl service = new UserServiceImpl();
		this.userService = service;
		userDao = Mockito.mock(UserDao.class);
		service.setUserDao(userDao);
	}

	@Test
	public void find() {
		User user = Mockito.mock(User.class);
		Mockito.when(userDao.getById(1)).thenReturn(user);

		assertSame(user, userService.getById(1));
	}

	@Test
	public void enable() {
		User user = new User("a", "a");
		user.disable();
		Mockito.when(userDao.getById(1)).thenReturn(user);

		userService.enableUser(1);

		assertTrue(user.isEnabled());
	}

	@Test
	public void disable() {
		User user = new User("a", "a");
		user.enable();
		Mockito.when(userDao.getById(1)).thenReturn(user);

		userService.disableUser(1);

		assertFalse(user.isEnabled());
	}
}
