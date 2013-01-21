package com.hermes.owasphotel.web.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.UserForm;
import com.hermes.owasphotel.web.security.UserAuthentication;

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
		return redirectTo(user == null ? null : user.getId());
	}

	private static String redirectTo(Integer id) {
		String r = "redirect:/user";
		if (id != null)
			r += "/" + id;
		return r;
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public String viewList(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		model.addAttribute("userTableType", "userTableEnable.jsp");
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "listNames")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseBody
	public List<String> getNames(@RequestParam(required = false) String prefix) {
		return userService.getNames(prefix);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	@PreAuthorize("hasRole('ADMIN') or #id == #auth.principal.id")
	public String viewUser(Model model, @PathVariable Integer id,
			Authentication auth) {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		return "user/view";
	}

	private User getuserForUpdate(Integer userId, Authentication auth) {
		if (auth == null)
			throw new AccessDeniedException("User not authentified");
		User user = userService.getById(userId);
		if (!(auth.getName().equals(user.getName()) || Utils.hasRole(auth,
				Role.ADMIN)))
			throw new AccessDeniedException("Cannot edit that profile");
		return user;
	}

	@RequestMapping(method = RequestMethod.GET, value = "update/{id}")
	public String viewUpdateUser(Model model, @PathVariable Integer id,
			Authentication auth) {
		User user = getuserForUpdate(id, auth);
		model.addAttribute("user", new UserForm(user));
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	public String viewCreateUser(@ModelAttribute("user") UserForm dto) {
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createUser(@Valid @ModelAttribute("user") UserForm dto,
			BindingResult binding, RedirectAttributes redirectAttrs) {
		if (!binding.hasErrors()) {
			try {
				User user = dto.makeNew();
				userService.save(user);

				// authenticate the user
				SecurityContext ctx = SecurityContextHolder.getContext();
				ctx.setAuthentication(new UserAuthentication(user));

				Utils.successMessage(redirectAttrs, "User '" + user.getName()
						+ "' created");
				return redirectTo(user);
			} catch (DataIntegrityViolationException e) {
				binding.addError(new ObjectError("user", "User "
						+ dto.getName() + " already exists "));
			} catch (IllegalArgumentException e) {
				binding.addError(new ObjectError("user", "Invalid parameters: "
						+ e.getMessage()));
			}
		}
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "update/{id}")
	public String updateUser(@PathVariable Integer id, Authentication auth,
			@Valid @ModelAttribute("user") UserForm dto, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		User user = getuserForUpdate(id, auth);
		try {
			boolean asAdmin = !auth.getName().equals(user.getName())
					&& Utils.hasRole(auth, Role.ADMIN);
			String oldName = user.getName();

			// update the user
			dto.update(user);
			dto.updatePassword(user, asAdmin);
			user = userService.update(user);

			// re-authenticate the user
			if (!asAdmin && !oldName.equals(user.getName())) {
				SecurityContext ctx = SecurityContextHolder.getContext();
				assert auth == ctx.getAuthentication();
				if (auth instanceof UserAuthentication) {
					((UserAuthentication) auth).refresh(userService);
				} else if (auth instanceof UsernamePasswordAuthenticationToken) {
					ctx.setAuthentication(new UsernamePasswordAuthenticationToken(
							dto.getName(), auth.getCredentials(), auth
									.getAuthorities()));
				} else {
					// not handled
				}
			}

			Utils.successMessage(redirectAttrs, "User updated.");
			return redirectTo(user);
		} catch (IllegalArgumentException e) {
			binding.addError(new ObjectError("User", e.getMessage()));
			return "user/update";
		}
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "enable/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String enableUser(@PathVariable Integer id,
			@RequestParam boolean enable, RedirectAttributes redirectAttrs) {
		if (enable) {
			userService.enableUser(id);
		} else {
			userService.disableUser(id);
		}
		Utils.successMessage(redirectAttrs, "User "
				+ (enable ? "enabled" : "disabled"));
		return redirectTo(id);
	}

}
