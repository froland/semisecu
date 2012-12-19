package com.hermes.owasphotel.web.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	private static String redirectTo(String name) {
		String r = "redirect:/user";
		if (name != null)
			r += "/" + name;
		return r;
	}

	@RequestMapping(method = RequestMethod.GET, value = "list")
	public String viewList(Model model) {
		List<User> users = userService.findAll();
		model.addAttribute("users", users);
		return "user/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "{name}")
	public String viewUser(Model model, @PathVariable String name) {
		User user = userService.find(name);
		if (user == null)
			throw new ResourceNotFoundException(User.class, null);
		model.addAttribute("user", user);
		return "user/view";
	}

	@RequestMapping(method = RequestMethod.GET, value = "update/{id}")
	public String viewUpdateUser(Model model, @PathVariable Integer id) {
		User user = userService.find(id);
		if (user == null)
			throw new ResourceNotFoundException(User.class, Long.valueOf(id));
		model.addAttribute("user", new UserDto(user));
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	public String viewCreateHotel(@ModelAttribute("user") UserDto dto) {
		return "user/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createHotel(@Valid @ModelAttribute("user") UserDto dto,
			BindingResult binding) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		User user = dto.makeNew();
		userService.save(user);
		return redirectTo(user.getName());
	}

	@RequestMapping(method = RequestMethod.POST, value = "update/{id}")
	public String updateUser(@PathVariable Integer id,
			@Valid @ModelAttribute("user") UserDto dto, BindingResult binding) {
		if (binding.hasErrors()) {
			return "user/update";
		}
		userService.update(dto);
		return redirectTo(dto.getName());
	}

}
