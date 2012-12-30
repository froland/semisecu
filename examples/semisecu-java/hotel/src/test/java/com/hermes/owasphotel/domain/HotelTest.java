package com.hermes.owasphotel.domain;

import org.junit.Test;
import static org.junit.Assert.*;

public class HotelTest {
	@Test
	public void testCreate() {
		User manager = new User("a", "a");
		Hotel h = new Hotel("h", manager);
		assertEquals("h", h.getHotelName());
		assertSame(manager, h.getManager());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		new Hotel("", new User("a", "a"));
	}

	@Test
	public void testApprove() {
		Hotel h = new Hotel("h", new User("a", "a"));
		assertFalse("Hotel must not be approved when created", h.isApproved());
		h.approveHotel();
		assertTrue("Hotel was not approved", h.isApproved());
	}

	@Test
	public void testStars() {
		Hotel h = new Hotel("h", new User("a", "a"));
		assertNull(h.getStars());
		Integer s = 3;
		h.setStars(s);
		assertEquals(s, h.getStars());
		try {
			h.setStars(-2);
			fail("Cannot set negative stars");
		} catch (IllegalArgumentException e) {
			// ok
		}
		try {
			h.setStars(6);
			fail("Maximum 5 stars");
		} catch (IllegalArgumentException e) {
			// ok
		}
	}

	@Test
	public void testAddComment() {
		Hotel h = new Hotel("h", new User("a", "a"));
		Comment c1 = h.addComment(new User("b", "b"));
		c1.setText("hello");
		assertEquals("b", c1.getUserName());
		assertTrue("Comment not added", h.getComments().contains(c1));
		Comment c2 = h.addComment(null);
		c2.setUserName("me");
		assertEquals("me", c2.getUserName());
	}

	@Test
	public void testCountComments() {
		Hotel h = new Hotel("h", new User("a", "a"));
		User u = new User("b", "a");
		Comment c1 = h.addComment(u);
		h.addComment(u);
		c1.delete();
		assertEquals(2, h.getNbComments(true));
		assertEquals(1, h.getNbComments(false));
	}

	@Test
	public void testAverageNote() {
		Hotel h = new Hotel("h", new User("a", "a"));
		assertNull(h.getAverageNote());
		Double note = 8d;
		h.setAverageNote(note);
		assertSame(note, h.getAverageNote());
		h.setAverageNote(null);
	}
}
