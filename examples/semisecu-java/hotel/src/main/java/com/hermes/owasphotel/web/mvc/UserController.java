package com.hermes.owasphotel.web.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	private static String redirectTo(Integer id) {
		String r = "redirect:/user";
		if (id != null)
			r += "/" + id;
		return r;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewUser(Model model, @PathVariable Integer id) {
		User user = userService.find(id);
		if (user == null)
			throw new ResourceNotFoundException(User.class, id.longValue());
		model.addAttribute("user", user);
		return "user/view";
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
		return redirectTo(user.getId());
	}

}
