package com.hermes.owasphotel.web.mvc;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
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
	public void export(HttpServletResponse response, @RequestParam String tableName) {
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment;filename=dumpTable"+tableName+".csv");
		
		
		try{
			
			adminService.dumpToWriter(tableName, response.getWriter());
		}
		catch (DataAccessException e)
		{
			try {
				response.sendError(404, "Unable to fetch data");
			} catch (IOException e1) {
				System.out.println("SqlError");
				e1.printStackTrace();
			}
		}
		catch (Exception e)
		{
			try {
				response.sendError(500, "Sending data failed due to unknow error");
			} catch (IOException e1) {
				//What could I do?
				System.out.println("Error");
				e1.printStackTrace();
			}
			
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "view")
	public String viewAdmin() {
		return "admin/view";
	}
	

}
