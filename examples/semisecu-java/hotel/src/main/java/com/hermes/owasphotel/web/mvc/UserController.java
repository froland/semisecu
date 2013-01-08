package com.hermes.owasphotel.web.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.UserForm;

/**
 * Controller for users.
 * 
 * @author v
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// property editor for dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	private static String redirectTo(User user) {
		String r = "redirect:/user";
		if (user != null)
			r += "/" + user.getName();
		return r;
	}

	private static void checkEditProfile(User user, Authentication auth) {
		if (auth == null
				|| user == null
				|| !(auth.getName().equals(user.getName()) || Utils.hasRole(
						auth, Role.ADMIN)))
			throw new AccessDeniedException("Cannot edit that profile");
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('admin')")
	public String viewList(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "listNames")
	@PreAuthorize("hasRole('admin')")
	@ResponseBody
	public List<String> getNames(@RequestParam(required = false) String prefix) {
		return userService.getNames(prefix);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{name}")
	@PreAuthorize("hasRole('admin') or #name == authentication.name")
	public String viewUser(Model model, @PathVariable String name) {
		User user = userService.getByName(name);
		if (user == null)
			throw new IllegalArgumentException("User not found: name=" + name);
		model.addAttribute("user", user);
		return "user/view";
	}

	@RequestMapping(method = RequestMethod.GET, value = "update/{id}")
	public String viewUpdateUser(Model model, @PathVariable Integer id,
			Authentication auth) {
		User user = userService.getById(id);
		checkEditProfile(user, auth);
		model.addAttribute("user", new UserForm(user));
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	public String viewCreateUser(@ModelAttribute("user") UserForm dto) {
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createUser(@Valid @ModelAttribute("user") UserForm dto,
			BindingResult binding) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		try {
			User user = dto.makeNew();
			userService.save(user);
			return redirectTo(user);
		} catch (PersistenceException e) {
			binding.addError(new ObjectError("User", "User " + dto.getName()
					+ " already exists "));
			return "user/update";
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "update/{id}")
	public String updateUser(@PathVariable Integer id, Authentication auth,
			@Valid @ModelAttribute("user") UserForm dto, BindingResult binding) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		User user = userService.getById(id);
		checkEditProfile(user, auth);
		try {
			boolean asAdmin = !auth.getName().equals(user.getName())
					&& Utils.hasRole(auth, Role.ADMIN);
			String oldName = user.getName();

			// update the user
			dto.update(user);
			dto.updatePassword(user, asAdmin);
			user = userService.update(user);

			// re-authentify the user
			if (!asAdmin && !oldName.equals(user.getName())) {
				SecurityContext ctx = SecurityContextHolder.getContext();
				assert auth == ctx.getAuthentication();
				ctx.setAuthentication(new UsernamePasswordAuthenticationToken(
						dto.getName(), auth.getCredentials(), auth
								.getAuthorities()));
			}

			return redirectTo(user);
		} catch (IllegalArgumentException e) {
			binding.addError(new ObjectError("User", e.getMessage()));
			return "user/update";
		}
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "enable/{id}")
	@PreAuthorize("hasRole('admin')")
	public String enableUser(@PathVariable Integer id,
			@RequestParam boolean enable) {
		User user;
		if (enable)
			user = userService.enableUser(id);
		else
			user = userService.disableUser(id);
		return redirectTo(user);
	}

}
