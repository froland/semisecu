package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.UserForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserControllerTest.Config.class)
public class UserControllerTest extends ControllerTestBase<UserController> {
	public UserControllerTest() {
		super(UserController.class);
	}

	@Test
	public void viewUsers() throws Exception {
		request(HttpMethod.GET, "/user");
		assertNotNull(mav.getViewName());
		assertType(mav, "users", List.class);
	}

	@Test
	public void viewUser() throws Exception {
		request(HttpMethod.GET, "/user/a");
		assertResponse(HttpStatus.OK);
		assertType(mav, "user", User.class);
	}

	@Test
	public void viewUpdateUser() throws Exception {
		authentify("a", null, "user");
		request(HttpMethod.GET, "/user/update/1");
		assertResponse(HttpStatus.OK);
		assertType(mav, "user", UserForm.class);
	}

	@Test
	public void postUpdateUser() throws Exception {
		authentify("a", null, "user");
		request.setParameter("name", "updateName");
		request(HttpMethod.POST, "/user/update/1");
		assertNoBindingErrors(mav);
		assertRedirect(mav);
	}

	@Test(expected = AccessDeniedException.class)
	public void viewUpdateOtherUser() throws Exception {
		request(HttpMethod.GET, "/user/update/1");
	}

	@Test(expected = IllegalArgumentException.class) // TODO
	public void viewUpdateIncorrectUser() throws Exception {
		request(HttpMethod.GET, "/user/update/2");
	}

	@Test
	public void postEnable() throws Exception {
		request.addParameter("enable", "true");
		request(HttpMethod.POST, "/user/enable/1");
		assertRedirect(mav);
	}

	@Test(expected = HttpRequestMethodNotSupportedException.class)
	public void getEnable() throws Exception {
		request.addParameter("enable", "true");
		request(HttpMethod.GET, "/user/enable/1");
	}
	
	/*
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
	*/

	@Configuration
	@Import(ControllerTestBase.WebConfiguration.class)
	public static class Config {
		@Bean
		public UserController userController() {
			return new UserController();
		}

		@Bean
		public UserService userService() {
			UserService service = Mockito.mock(UserService.class);
			User a = new User("a", "a");
			Mockito.when(service.getById(1)).thenReturn(a);
			Mockito.when(service.getByName(a.getName())).thenReturn(a);
			return service;
		}
	}
}
