package com.hermes.owasphotel.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hermes.owasphotel.domain.User;
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
	
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewUser(Model model, @PathVariable Integer id) {
		User user = userService.find(id);
		if (user == null)
			throw new ResourceNotFoundException(User.class, id.longValue());
		model.addAttribute("user", user);
		return "user/view";
	}

}
