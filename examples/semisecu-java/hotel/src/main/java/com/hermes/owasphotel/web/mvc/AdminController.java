package com.hermes.owasphotel.web.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hermes.owasphotel.service.AdminService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	@Autowired
	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "export")
	public void export(HttpServletResponse response,
			@RequestParam String tableName,
			@RequestParam("col") String[] columns) throws IOException {

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment;filename=dumpTable" + tableName + ".csv");

		adminService.dumpToWriter(tableName, columns, response.getWriter());
	}

	@RequestMapping(method = RequestMethod.GET, value = "export/tableList")
	@ResponseBody
	public List<String> getTableList() {
		return adminService.listTables();
	}

	@RequestMapping(method = RequestMethod.GET, value = "export/tableColumns")
	@ResponseBody
	public List<String> getTableColumns(@RequestParam String tableName) {
		return adminService.listColumns(tableName);
	}

	@RequestMapping(method = RequestMethod.GET, value = "tableColumns")
	public String viewTableColumns(Model model, @RequestParam String tableName) {
		model.addAttribute("columns", getTableColumns(tableName));
		return "admin/tableColumns";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewAdmin(Model model) {
		List<String> tables = adminService.listTables();
		String tablesJSON = "[]";
		try {
			tablesJSON = new ObjectMapper().writeValueAsString(tables);
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("tables", tablesJSON);
		return "admin/view";
	}

}
