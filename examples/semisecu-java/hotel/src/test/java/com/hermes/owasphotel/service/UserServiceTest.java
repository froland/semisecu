package com.hermes.owasphotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.web.mvc.form.UserForm;

public class UserServiceTest extends ServiceTestBase {
	@Autowired
	private UserService userService;

	@Test
	public void testFind() {
		User user = userService.getById(1857);
		assertNull(user);

		user = new User("a", "a");
		userService.save(user);
		assertNotNull("User not saved", userService.getById(user.getId()));
		assertTrue("User name not returned by getNames()", userService
				.getNames("").contains("a"));
		assertFalse("User name returned by getNames() with a bad prefix",
				userService.getNames("ab").contains("a"));
	}

	@Test
	public void testUpdate() {
		User u = new User("a", "p");
		userService.save(u);

		// create the form
		UserForm user = new UserForm(u);
		assertNull("The old password was read from the user",
				user.getOldPassword());
		assertNull("The password is shown", user.getPassword());

		// update the e-mail
		String newEmail = "hello@test.com";
		user.setEmail(newEmail);
		// u = userService.update(user, false); // TODO
		assertEquals("Failed to update the e-mail", newEmail, u.getEmail());
		assertTrue("Password updated with e-mail", u.checkPassword("p"));
	}

	@Test
	public void testUpdatePassword() {
		User u = new User("a", "p");
		userService.save(u);
		UserForm user = new UserForm(u);

		// update without giving the old password
		user.setPassword("z");
		user.setRetypedPassword("z");
		// u = userService.update(user, false); // TODO
		assertTrue("Password updated without giving the old password",
				u.checkPassword("p"));

		// update
		user.setOldPassword("p");
		// u = userService.update(user, false); // TODO
		assertFalse("Password not updated", u.checkPassword("p"));
		assertTrue("New password is not working", u.checkPassword("z"));
	}

	@Test
	public void testUpdatePasswordAsAdmin() {
		User u = new User("a", "p");
		userService.save(u);
		UserForm user = new UserForm(u);

		// update without giving the old password (as admin)
		user.setPassword("z");
		user.setRetypedPassword("z");
		// u = userService.update(user, true); // TODO
		assertTrue("New password is not working", u.checkPassword("z"));
	}

	@Test
	public void testEnable() {
		User u = new User("a", "p");
		userService.save(u);
		final Integer id = u.getId();

		u = userService.enableUser(id, false);
		assertFalse(u.isEnabled());
		u = userService.enableUser(id, true);
		assertTrue(u.isEnabled());
	}
}
