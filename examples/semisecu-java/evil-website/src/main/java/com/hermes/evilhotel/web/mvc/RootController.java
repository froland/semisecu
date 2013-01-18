package com.hermes.evilhotel.web.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class RootController {
	@RequestMapping(method = RequestMethod.GET)
	public String viewIndex() {
		return "index";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "sendData")
	public String viewAttack(@RequestParam("data") String data) {
		System.out.println(data);
		return "attack";
	}

}
