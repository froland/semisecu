package com.hermes.owasphotel.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hermes.owasphotel.domain.Comment;
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
		Comment c = h.addComment(null);
		c.setUserName("me");
		c.setNote(1);
		return h;
	}

	protected List<Hotel> multipleHotels;
	protected List<Hotel> multipleHotelsManaged;
	private Hotel multipleTopNotedHotel;
	protected User multipleManager;

	protected void initializeMultipleHotels() {
		multipleHotels = new ArrayList<Hotel>();
		multipleHotelsManaged = new ArrayList<Hotel>();
		multipleManager = new User("some manager", "a");
		userDao.save(multipleManager);

		multipleTopNotedHotel = addMultipleHotel("top", multipleManager, 10);
		addMultipleHotel("h1", multipleManager);

		User other = new User("ben", "ben");
		userDao.save(other);

		addMultipleHotel("h2", other, 8);
		addMultipleHotel("h3", other, 3, 5);
	}

	private Hotel addMultipleHotel(String name, User manager, int... notes) {
		Hotel h = new Hotel(name, manager);
		h.approveHotel();
		for (int note : notes) {
			h.addComment(null).setNote(note);
		}
		hotelDao.save(h);
		multipleHotels.add(h);
		if (manager == multipleManager)
			multipleHotelsManaged.add(h);
		return h;
	}

	@Test
	public void testFindManaged() {
		initializeMultipleHotels();
		List<Hotel> managed = hotelDao.findManagedHotels(multipleManager);
		assertTrue(
				"Managed hotels do not match",
				multipleHotelsManaged.containsAll(managed)
						&& managed.containsAll(multipleHotelsManaged));
	}

	@Test
	public void testFindApproved() {
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
	public void testFindTopHotels() {
		initializeMultipleHotels();
		List<Hotel> top = hotelDao.findTopNotedHotels(3);
		int count = Math.min(3, multipleHotels.size());
		assertEquals("Invalid number of top hotels", count, top.size());
		checkEquals(multipleTopNotedHotel, top.get(0));
		hotelDao.computeNote(multipleTopNotedHotel);
		for (int i = 1; i < count; i++) {
			hotelDao.computeNote(top.get(i));
			assertTrue("Invalid order at index: " + (i - 1) + "-" + i,
					top.get(i - 1).getAverageNote() > top.get(i)
							.getAverageNote());
		}
	}

	@Test
	public void testFindByName() {
		Hotel h = createEntity();
		h.setHotelName("my hotel");
		h = hotelDao.save(h);

		assertNull(hotelDao.findByName("___xyz"));
		Hotel found = hotelDao.findByName("my hotel");
		checkEquals(h, found);
	}

	@Test
	public void testFindSearchQuery() {
		Hotel h = createEntity();
		h.setHotelName("my hotel");
		h = hotelDao.save(h);
		List<Hotel> list;

		list = hotelDao.findSearchQuery("hot", true, 2);
		assertTrue(list.contains(h));
		list = hotelDao.findSearchQuery("HOT", true, 2);
		assertTrue("Query should be case-insensitive", list.contains(h));
		list = hotelDao.findSearchQuery("my", false, 2);
		assertTrue(list.contains(h));
		list = hotelDao.findSearchQuery("hot", false, 2);
		assertFalse(list.contains(h));
	}

	@Test
	public void testComputedNote() {
		Hotel h = createEntity();
		h.addComment(null).setNote(8);
		h.addComment(null).setNote(3);
		h.addComment(null).setNote(7);
		double note = 0;
		for (Comment c : h.getComments()) {
			note += c.getNote();
		}
		note /= h.getComments().size();
		h = hotelDao.save(h);

		hotelDao.computeNote(h);
		assertEquals(note, h.getAverageNote(), 0.1d);
	}

}
