package com.hermes.owasphotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	@Test
	public void newUserName() {
		User a = new User("a", "pass");
		assertEquals("Name is ignored", "a", a.getName());
	}

	@Test
	public void newUserPassword() {
		User a = new User("a", "pass");
		assertTrue("Password check failed",
				a.checkPassword("pass") && !a.checkPassword("a"));
	}

	@Test
	public void newUserIsNotAdmin() {
		User a = new User("a", "pass");
		assertFalse("By default the user is not an admin", a.isAdmin());
	}

	@Test
	public void newUserHasUserRole() {
		User a = new User("a", "pass");
		assertTrue("Check role user", a.getRoles().contains(Role.USER));
	}

	@Test(expected = IllegalArgumentException.class)
	public void initWithEmptyName() {
		new User("", "pass");
	}

	@Test(expected = IllegalArgumentException.class)
	public void initWithEmptyPassword() {
		new User("a", "");
	}

	@Test
	public void setEnabled() {
		User a = new User("a", "pass");
		assertTrue("By default the user is enabled", a.isEnabled());
		a.disable();
		assertFalse(a.isEnabled());
		a.enable();
		assertTrue(a.isEnabled());
	}

	@Test
	public void setAdmin() {
		User a = new User("a", "pass");
		assertFalse(a.isAdmin());
		a.setAdmin(true);
		assertTrue(a.isAdmin());
		assertTrue("The admin role should be present when admin", a.getRoles()
				.contains(Role.ADMIN));
		a.setAdmin(false);
		assertFalse(a.isAdmin());
		assertFalse(a.getRoles().contains(Role.ADMIN));
	}
}
