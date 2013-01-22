package com.hermes.owasphotel.web.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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

/**
 * The administrator controller.
 */
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

	/**
	 * Exports a table as a CSV file.
	 * @param response The response
	 * @param tableName The table name
	 * @param columns The array of columns
	 * @throws IOException when the file couldn't be written
	 * @see AdminService#dumpToWriter(String, String[], java.io.Writer)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "export")
	public void export(HttpServletResponse response,
			@RequestParam String tableName,
			@RequestParam("col") String[] columns) throws IOException {

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition",
				"attachment;filename=dumpTable" + tableName + ".csv");

		adminService.dumpToWriter(tableName, columns, response.getWriter());
	}

	/**
	 * Gets the list of tables.
	 * @return The list of tables
	 * @see AdminService#listTables()
	 */
	@RequestMapping(method = RequestMethod.GET, value = "export/tableList")
	@ResponseBody
	public List<String> getTableList() {
		return adminService.listTables();
	}

	/**
	 * Gets the list of columns.
	 * @param tableName The table name
	 * @return The list of columns
	 * @see AdminService#listColumns(String)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "export/tableColumns")
	@ResponseBody
	public List<String> getTableColumns(@RequestParam String tableName) {
		return adminService.listColumns(tableName);
	}

	/**
	 * View for table columns.
	 * @param model The model
	 * @param tableName The table name
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "tableColumns")
	public String viewTableColumns(Model model, @RequestParam String tableName) {
		model.addAttribute("columns", getTableColumns(tableName));
		return "admin/tableColumns";
	}

	/**
	 * View for the admin page.
	 * @param model The model
	 * @return The view
	 * @throws JsonGenerationException when the list of tables couldn't be generated
	 * @throws JsonMappingException when the list of tables couldn't be generated
	 * @throws IOException when the list of tables couldn't be written
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String viewAdmin(Model model) throws JsonGenerationException,
			JsonMappingException, IOException {
		List<String> tables = adminService.listTables();
		String tablesJSON = new ObjectMapper().writeValueAsString(tables);
		model.addAttribute("tables", tablesJSON);
		return "admin/view";
	}

}
