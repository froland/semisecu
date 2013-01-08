package com.hermes.owasphotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {
	@Test
	public void testCreate() {
		User a = new User("a", "pass");
		assertEquals("Name is ignored", "a", a.getName());
		assertTrue("Password check failed",
				a.checkPassword("pass") && !a.checkPassword("a"));
		assertFalse("By default the user is not an admin", a.isAdmin());

		assertTrue("Check role user", a.getRoles().contains(Roles.user));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyName() {
		new User("", "pass");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyPassword() {
		new User("a", "");
	}

	@Test
	public void testSetEnabled() {
		User a = new User("a", "pass");
		assertTrue("By default the user is enabled", a.isEnabled());
		a.disable();
		assertFalse(a.isEnabled());
		a.enable();
		assertTrue(a.isEnabled());
	}

	@Test
	public void testSetAdmin() {
		User a = new User("a", "pass");
		assertFalse(a.isAdmin());
		a.setAdmin(true);
		assertTrue(a.isAdmin());
		assertTrue("The admin role should be present when admin", a.getRoles()
				.contains(Roles.admin));
		a.setAdmin(false);
		assertFalse(a.isAdmin());
		assertFalse(a.getRoles().contains(Roles.admin));
	}
}
