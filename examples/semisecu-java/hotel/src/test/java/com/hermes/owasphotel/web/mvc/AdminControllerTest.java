package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import com.hermes.owasphotel.service.AdminService;

public class AdminControllerTest extends ControllerTestBase<AdminController> {

	private AdminService adminService;

	public AdminControllerTest() {
		super(AdminController.class);
	}

	@Override
	@Before
	public void initController() throws Exception {
		super.initController();
		adminService = Mockito.mock(AdminService.class);
		controller.setAdminService(adminService);
	}

	@Test
	public void getTableList() throws Exception {
		List<String> list = new ArrayList<String>();
		Mockito.when(adminService.listTables()).thenReturn(list);

		assertSame(list, controller.getTableList());
	}

	@Test
	public void viewDefault() throws Exception {
		Model model = createModel();

		assertNotNull(controller.viewAdmin(model));
		assertFalse("Empty tables JSON string",
				assertType(model.asMap(), "tables", String.class).isEmpty());
	}

	@Test
	public void viewExport() throws Exception {
		String tableName = "t";
		String[] columns = new String[] { "c1", "c2" };

		controller.export(response, tableName, columns);

		Mockito.verify(adminService).dumpToWriter(tableName, columns,
				response.getWriter());
	}
}
