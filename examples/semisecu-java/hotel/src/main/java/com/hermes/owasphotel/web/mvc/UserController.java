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
import org.springframework.security.core.Authentication;
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

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserDto;
import com.hermes.owasphotel.service.UserService;

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
						auth, "admin")))
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
	@PreAuthorize("hasRole('admin') or #name == principal.username")
	public String viewUser(Model model, @PathVariable String name) {
		User user = userService.find(name);
		if (user == null)
			throw new ResourceNotFoundException(User.class, null);
		model.addAttribute("user", user);
		return "user/view";
	}

	@RequestMapping(method = RequestMethod.GET, value = "update/{id}")
	public String viewUpdateUser(Model model, @PathVariable Integer id,
			Authentication auth) {
		User user = userService.find(id);
		if (user == null)
			throw new ResourceNotFoundException(User.class, Long.valueOf(id));
		checkEditProfile(user, auth);
		UserDto dto = new UserDto();
		dto.read(user);
		model.addAttribute("user", dto);
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	public String viewCreateUser(@ModelAttribute("user") UserDto dto) {
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createUser(@Valid @ModelAttribute("user") UserDto dto,
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
			@Valid @ModelAttribute("user") UserDto dto, BindingResult binding) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		checkEditProfile(userService.find(id), auth);
		User user = userService.update(dto);
		return redirectTo(user);
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "enable/{id}")
	@PreAuthorize("hasRole('admin')")
	public String enableUser(@PathVariable Integer id,
			@RequestParam boolean enable) {
		User user = userService.enableUser(id, enable);
		return redirectTo(user);
	}
}
