package com.hermes.owasphotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HotelTest {
	@Test
	public void create() {
		User manager = new User("a", "a");
		Hotel h = new Hotel("h", manager);
		assertEquals("h", h.getName());
		assertSame(manager, h.getManager());
	}

	@Test(expected = IllegalArgumentException.class)
	public void initWithEmptyName() {
		new Hotel("", new User("a", "a"));
	}

	@Test
	public void approve() {
		Hotel h = new Hotel("h", new User("a", "a"));
		assertFalse("Hotel must not be approved when created", h.isApproved());
		h.approveHotel();
		assertTrue("Hotel was not approved", h.isApproved());
	}

	@Test
	public void stars() {
		Hotel h = new Hotel("h", new User("a", "a"));
		assertNull(h.getStars());
		Integer s = 3;
		h.setStars(s);
		assertEquals(s, h.getStars());
	}

	@Test(expected = IllegalArgumentException.class)
	public void starsNegative() {
		Hotel h = new Hotel("h", new User("a", "a"));
		h.setStars(-2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void starsTooMuch() {
		Hotel h = new Hotel("h", new User("a", "a"));
		h.setStars(6);
	}

	@Test
	public void addComment() {
		Hotel h = new Hotel("h", new User("a", "a"));
		Comment c1 = h.createComment(new User("b", "b"), 5, "hello");
		assertEquals("b", c1.getUserName());
		assertTrue("Comment not added", h.getComments().contains(c1));
	}

	@Test
	public void countComments() {
		Hotel h = new Hotel("h", new User("a", "a"));
		User u = new User("b", "a");
		Comment c1 = h.createComment(u, 1, "");
		h.createComment(u, 1, "");
		c1.delete();
		assertEquals(2, h.getNbComments(true));
		assertEquals(1, h.getNbComments(false));
	}
}
