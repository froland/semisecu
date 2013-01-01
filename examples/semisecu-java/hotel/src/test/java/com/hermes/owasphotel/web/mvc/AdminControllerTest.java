package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.io.Writer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.hermes.owasphotel.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AdminControllerTest.Config.class)
public class AdminControllerTest extends ControllerTestBase<AdminController> {

	public AdminControllerTest() {
		super(AdminController.class);
	}

	@Test
	public void getTableList() throws Exception {
		request.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
		request(HttpMethod.GET, "/admin/export/tableList");
		assertResponse(HttpStatus.OK);
		assertFalse(response.getContentAsString().isEmpty());
	}

	@Test
	public void viewDefault() throws Exception {
		request(HttpMethod.GET, "/admin");
		assertFalse("Empty tables JSON string",
				assertType(mav, "tables", String.class).isEmpty());
	}

	@Test
	public void viewExport() throws Exception {
		request.addParameter("tableName", "a");
		request.addParameter("col", "c1");
		request.addParameter("col", "c2");
		request(HttpMethod.GET, "/admin/export");
		assertResponse(HttpStatus.OK);
		assertEquals("a", response.getContentAsString());
	}

	@Test(expected = MissingServletRequestParameterException.class)
	public void viewExportMissingParameter() throws Exception {
		request(HttpMethod.GET, "/admin/export");
	}

	@Configuration
	@Import(ControllerTestBase.WebConfiguration.class)
	public static class Config {
		@Bean
		public AdminController adminController() {
			return new AdminController();
		}

		@Bean
		public AdminService adminService() throws IOException {
			AdminService service = Mockito.mock(AdminService.class);
			Mockito.doAnswer(new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocation)
						throws Throwable {
					Writer w = (Writer) invocation.getArguments()[2];
					w.append(invocation.getArguments()[0].toString());
					return null;
				}
			})
					.when(service)
					.dumpToWriter(Mockito.anyString(),
							Mockito.<String[]> any(), Mockito.<Writer> any());
			return service;
		}
	}
}
