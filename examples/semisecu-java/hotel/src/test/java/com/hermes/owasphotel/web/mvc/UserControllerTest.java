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
