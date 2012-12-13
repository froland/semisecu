package com.hermes.owasphotel.web.mvc;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hermes.owasphotel.service.AdminService;

@Controller
@RequestMapping("/admin")

public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(method = RequestMethod.GET, value = "export")
	public String export(Model model, @RequestParam String tableName) {
		String s;
		try{
			s = adminService.getDumpString(tableName);
		}
		catch (IOException e)
		{
			s = e.getLocalizedMessage();
		}
		model.addAttribute("dump", s);
		return "admin/export-db";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "view")
	public String viewAdmin() {
		return "admin/view";
	}
	

}
