package com.hermes.owasphotel.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The root controller.
 */
@Controller
@RequestMapping("/")
public class RootController {
	/**
	 * View the home page.
	 * @return Redirect to hotels
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String viewIndex() {
		return "redirect:/hotel";
	}

	/**
	 * View the login page.
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "login")
	public String viewLogin() {
		return "login/login";
	}
}
