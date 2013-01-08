package com.hermes.owasphotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.hermes.owasphotel.dao.Dumper;
import com.hermes.owasphotel.service.impl.AdminServiceImpl;

public class AdminServiceTest {
	private AdminService adminService;
	private Dumper dumper;

	@Before
	public void initService() throws Exception {
		AdminServiceImpl adminService = new AdminServiceImpl();
		this.adminService = adminService;
		dumper = Mockito.mock(Dumper.class);
		adminService.setDumper(dumper);
		Mockito.when(dumper.listTables()).thenReturn(Arrays.asList("t1", "t2"));
		Mockito.when(dumper.listColumns("t1")).thenReturn(
				Arrays.asList("a", "b", "c"));
		Mockito.when(dumper.listColumns("t2")).thenReturn(Arrays.asList("c1"));
	}

	@Test
	public void listTablesColumns() throws Exception {
		assertTrue(adminService.listTables().contains("t2"));
		assertTrue(adminService.listColumns("t1").containsAll(
				Arrays.asList("a", "b", "c")));
	}

	@Test
	public void dumpCalled() throws Exception {
		final String table = "t1";
		final String[] columns = new String[] { "a", "b" };
		StringWriter sw = new StringWriter();

		Mockito.doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				((Writer) invocation.getArguments()[2]).append("hello");
				return null;
			}
		}).when(dumper).dump(table, columns, sw);

		adminService.dumpToWriter(table, columns, sw);

		Mockito.verify(dumper).dump(table, columns, sw);
		assertEquals("String not written", 5, sw.toString().length());
	}
}
