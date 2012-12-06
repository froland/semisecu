package com.hermes.owasphotel.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class RootController {
	@RequestMapping(method = RequestMethod.GET)
	public String viewIndex() {
		return "redirect:/hotel";
	}

	@RequestMapping(method = RequestMethod.GET, value = "login")
	public String viewLogin() {
		return "login/login";
	}
}
