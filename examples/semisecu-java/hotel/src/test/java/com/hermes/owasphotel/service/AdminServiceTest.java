package com.hermes.owasphotel.service;

import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest extends ServiceTestBase {
	@Autowired
	private AdminService adminService;

	@Test
	public void testDump() throws Exception {
		StringWriter sw = new StringWriter();
		String table = adminService.listTables().get(0);
		List<String> columns = adminService.listColumns(table);
		columns.remove(1);
		adminService.dumpToWriter(table, columns.toArray(new String[0]), sw);
		assertTrue("Nothing was written", sw.toString().length() > 0);
	}
}
