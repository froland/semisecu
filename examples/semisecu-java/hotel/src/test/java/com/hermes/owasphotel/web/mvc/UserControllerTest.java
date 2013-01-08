package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.invocation.Invocation;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.verification.VerificationMode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.UserForm;

public class UserControllerTest extends ControllerTestBase<UserController> {

	private UserService userService;

	public UserControllerTest() {
		super(UserController.class);
	}

	@Override
	@Before
	public void initController() throws Exception {
		super.initController();
		controller.setUserService(userService = createUserService());
	}

	private UserService createUserService() {
		UserService service = Mockito.mock(UserService.class);
		User a = new User("a", "a");
		Mockito.when(service.getById(1)).thenReturn(a);
		Mockito.when(service.getByName(a.getName())).thenReturn(a);
		return service;
	}

	protected Authentication createAuthentication(User user) {
		return createAuthentication(user.getName(),
				user.getRoles().toArray(new Role[0]));
	}

	@Test
	public void viewUsers() throws Exception {
		Model model = createModel();
		List<User> userList = new ArrayList<User>();
		Mockito.when(userService.findAll()).thenReturn(userList);

		assertNotNull(controller.viewList(model));
		assertSame(userList, model.asMap().get("users"));
	}

	@Test
	public void viewUser() throws Exception {
		Model model = createModel();

		assertNotNull(controller.viewUser(model, "a"));
		assertType(model.asMap(), "user", User.class);
	}

	@Test
	public void viewUpdateUser() throws Exception {
		Model model = createModel();
		Integer id = 1;
		Authentication auth = createAuthentication("a", Role.USER);

		assertNotNull(controller.viewUpdateUser(model, id, auth));
		assertType(model.asMap(), "user", UserForm.class);
	}

	@Test
	public void postUpdateUser() throws Exception {
		Integer id = 1;
		Authentication auth = createAuthentication("a", Role.USER);
		UserForm form = new UserForm(userService.getById(id));
		BindingResult binding = createBindingResult("form");
		User u = userService.getById(1);
		Mockito.when(userService.update(u)).thenReturn(u);

		assertRedirect(controller.updateUser(id, auth, form, binding));
		Mockito.verify(userService).update(Mockito.any(User.class));
	}

	@Test(expected = AccessDeniedException.class)
	public void viewUpdateOtherUser() throws Exception {
		Model model = createModel();
		Integer id = 1;
		Authentication auth = createAuthentication("b", Role.USER);

		controller.viewUpdateUser(model, id, auth);
	}

	@Test(expected = AccessDeniedException.class)
	public void viewUpdateIncorrectUser() throws Exception {
		Model model = createModel();
		Integer id = 2;
		Authentication auth = createAuthentication("a", Role.USER);

		controller.viewUpdateUser(model, id, auth);
	}

	@Test
	public void postEnable() throws Exception {
		assertRedirect(controller.enableUser(1, true));
		Mockito.verify(userService).enableUser(1);
	}

	@Test
	public void postDisable() throws Exception {
		assertRedirect(controller.enableUser(1, false));
		Mockito.verify(userService).disableUser(1);
	}

	private User getUserServiceUpdatedUser() {
		final AtomicReference<User> updatedUser = new AtomicReference<User>();
		VerificationMode updatedUserVerification = new VerificationMode() {
			@Override
			public void verify(VerificationData data) {
				for (Invocation inv : data.getAllInvocations()) {
					if (!data.getWanted().matches(inv))
						continue;
					if (updatedUser.get() != null)
						throw new IllegalStateException("Already updated");
					updatedUser.set((User) inv.getArguments()[0]);
				}
			}
		};
		Mockito.verify(userService, updatedUserVerification).update(
				Mockito.any(User.class));
		return updatedUser.get();
	}

	@Test
	public void updateWithForm() {
		User u = new User("a", "p");
		Mockito.when(userService.update(u)).thenReturn(u);
		Mockito.when(userService.getById(5)).thenReturn(u);
		Authentication auth = createAuthentication(u);

		// create the form
		Mockito.mock(UserForm.class);
		UserForm user = new UserForm(u);
		assertNull("The old password was read from the user",
				user.getOldPassword());
		assertNull("The password is shown", user.getPassword());

		// update the e-mail
		String newEmail = "hello@test.com";
		user.setEmail(newEmail);
		controller.updateUser(5, auth, user, createBindingResult("user"));

		User updatedUser = getUserServiceUpdatedUser();
		assertEquals("Failed to update the e-mail", newEmail,
				updatedUser.getEmail());
		assertTrue("Password updated with e-mail",
				updatedUser.checkPassword("p"));
	}

	@Test
	public void testUpdatePasswordWithoutOldPassword() {
		User u = new User("a", "p");
		Mockito.when(userService.update(u)).thenReturn(u);
		Mockito.when(userService.getById(5)).thenReturn(u);
		Authentication auth = createAuthentication(u);

		// update without giving the old password
		UserForm user = new UserForm(u);
		user.setPassword("z");
		user.setRetypedPassword("z");
		controller.updateUser(5, auth, user, createBindingResult("user"));

		u = getUserServiceUpdatedUser();
		assertTrue("Password updated without giving the old password",
				u.checkPassword("p"));
	}

	@Test
	public void testUpdatePassword() {
		User u = new User("a", "p");
		Mockito.when(userService.update(u)).thenReturn(u);
		Mockito.when(userService.getById(5)).thenReturn(u);
		Authentication auth = createAuthentication(u);

		// update without giving the old password
		UserForm user = new UserForm(u);
		user.setOldPassword("p");
		user.setPassword("z");
		user.setRetypedPassword("z");
		controller.updateUser(5, auth, user, createBindingResult("user"));

		u = getUserServiceUpdatedUser();
		assertFalse("Password not updated", u.checkPassword("p"));
		assertTrue("New password is not working", u.checkPassword("z"));
	}

	@Test
	public void testUpdatePasswordAsAdmin() {
		User u = new User("a", "p");
		Mockito.when(userService.update(u)).thenReturn(u);
		Mockito.when(userService.getById(5)).thenReturn(u);
		Authentication auth = createAuthentication("someadmin", Role.USER,
				Role.ADMIN);

		// update without giving the old password (as admin)
		UserForm user = new UserForm(u);
		user.setPassword("z");
		user.setRetypedPassword("z");
		controller.updateUser(5, auth, user, createBindingResult("user"));

		u = getUserServiceUpdatedUser();
		assertTrue("New password is not working", u.checkPassword("z"));
	}

}
