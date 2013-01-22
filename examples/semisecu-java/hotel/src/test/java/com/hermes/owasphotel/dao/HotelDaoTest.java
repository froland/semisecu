package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hermes.owasphotel.domain.Address;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

public class HotelDaoTest extends SimpleDaoTestBase<Integer, Hotel> {

	@Autowired
	private HotelDao hotelDao;

	@Autowired
	private UserDao userDao;

	@Override
	protected HotelDao getDao() {
		return hotelDao;
	}

	@Override
	protected Hotel createEntity() {
		User u = new User("u", "u");
		userDao.save(u);
		Hotel h = new Hotel("h", u);
		User me = new User("me", "me");
		userDao.save(me);
		h.createComment(me, 1, "");
		h.setAddress(new Address("", "", "", "", "H country"));
		return h;
	}

	private List<Hotel> multipleHotels;
	private List<Hotel> multipleHotelsManaged;
	private User multipleManager;

	private void initializeMultipleHotels() {
		multipleHotels = new ArrayList<Hotel>();
		multipleHotelsManaged = new ArrayList<Hotel>();
		multipleManager = new User("some manager", "a");
		userDao.save(multipleManager);
		userDao.flush();

		addMultipleHotel("top", multipleManager, 5);
		addMultipleHotel("h1", multipleManager);

		User other = new User("ben", "ben");
		userDao.save(other);
		userDao.flush();

		addMultipleHotel("h2", other, 4);
		addMultipleHotel("h3", other, 1, 3);
	}

	private Hotel addMultipleHotel(String name, User manager, int... notes) {
		Hotel h = new Hotel(name, manager);
		h.approveHotel();
		for (int note : notes) {
			h.createComment(null, note, "");
		}
		hotelDao.save(h);
		hotelDao.flush();
		multipleHotels.add(h);
		if (manager == multipleManager)
			multipleHotelsManaged.add(h);
		return h;
	}

	@Test
	public void findManaged() {
		initializeMultipleHotels();
		List<Hotel> managed = hotelDao.findManagedHotels(multipleManager);
		assertTrue(
				"Managed hotels do not match",
				multipleHotelsManaged.containsAll(managed)
						&& managed.containsAll(multipleHotelsManaged));
	}

	@Test
	public void findApproved() {
		initializeMultipleHotels();
		int count = 0;
		for (Hotel h : hotelDao.findAll()) {
			if (h.isApproved())
				count++;
		}
		assertTrue("Initialization failure: no approved hotels", count > 0);
		assertEquals(count, hotelDao.findApprovedHotels(true).size());
	}

	@Test
	public void findByName() {
		Hotel h = persistMyHotel();

		Hotel found = hotelDao.getByName("my hotel");
		assertEntityEquals(h, found);
	}

	@Test(expected = NoResultException.class)
	public void findByNameInexisting() {
		hotelDao.getByName("___xyz");
	}

	private Hotel persistMyHotel() {
		Hotel h = createEntity();
		h.setName("my hotel");
		h.approveHotel();
		hotelDao.save(h);
		hotelDao.flush();
		return h;
	}

	@Test
	public void findNotApproved() {
		Hotel h = createEntity();
		h.setName("test");
		hotelDao.save(h);
		hotelDao.flush();

		List<Hotel> list = hotelDao.findSearchQuery("test", false, 1);
		assertFalse("Found a not approved hotel", list.contains(h));
	}

	@Test
	public void findFullSearchQuery() {
		Hotel h = persistMyHotel();

		List<Hotel> list = hotelDao.findSearchQuery("hot", true, 2);
		assertTrue(list.contains(h));
	}

	@Test
	@Ignore("Code made case-sensitive for the demo")
	public void findSearchQueryInsensitive() {
		Hotel h = persistMyHotel();

		List<Hotel> list = hotelDao.findSearchQuery("HOT", true, 2);
		assertTrue("Query should be case-insensitive", list.contains(h));
	}

	@Test
	public void findSearchQuery() {
		Hotel h = persistMyHotel();

		List<Hotel> list = hotelDao.findSearchQuery("my", false, 2);
		assertTrue(list.contains(h));
	}

	@Test
	public void findSearchQueryNoMatch() {
		Hotel h = persistMyHotel();

		List<Hotel> list = hotelDao.findSearchQuery("hot", false, 2);
		assertFalse(list.contains(h));
	}

	@Test(expected = PersistenceException.class)
	public void unicity() {
		User u = new User("u", "u");
		userDao.save(u);
		Hotel h1 = new Hotel("test", u);
		h1.setAddress(new Address("", "", "", "", ""));
		hotelDao.save(h1);
		hotelDao.flush();
		Hotel h2 = new Hotel("test", u);
		h2.setAddress(new Address("", "", "", "", ""));
		hotelDao.save(h2);
		hotelDao.flush();
	}

	@Override
	protected void assertEntityEquals(Hotel expected, Hotel found) {
		super.assertEntityEquals(expected, found);
		assertEquals(expected.getAddress(), found.getAddress());
		assertEquals(expected.getImage(), found.getImage());
		assertEquals(expected.getComments().size(), found.getComments().size());
	}
}
