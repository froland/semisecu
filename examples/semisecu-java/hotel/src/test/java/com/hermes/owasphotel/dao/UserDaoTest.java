package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertNull;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest extends SimpleDaoTestBase<Integer, User> {
	@Autowired
	private UserDao userDao;

	@Override
	protected SimpleDao<Integer, User> getDao() {
		return userDao;
	}

	@Override
	protected User createEntity() {
		return new User("a", "b");
	}

	@Test
	public void testFindByName() {
		final String name = "testUser";
		User user = new User(name, "a");
		userDao.save(user);
		assertNull(userDao.find(name + "__"));
		User found = userDao.find(name);
		checkEquals(user, found);
	}

	@Test(expected = PersistenceException.class)
	public void testDuplicateUserName() {
		User u1 = new User("a", "a");
		User u2 = new User("a", "a");
		userDao.save(u1);
		userDao.save(u2);
	}
}
