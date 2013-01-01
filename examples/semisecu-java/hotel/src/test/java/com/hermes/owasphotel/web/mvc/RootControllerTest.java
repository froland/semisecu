package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

public class RootControllerTest extends ControllerTestBase<RootController> {
	public RootControllerTest() {
		super(RootController.class);
	}

	@Test
	public void viewRoot() throws Exception {
		request(HttpMethod.GET, "/");
		assertNotNull(mav.getViewName());
	}

	@Test(expected = HttpRequestMethodNotSupportedException.class)
	public void postRoot() throws Exception {
		request(HttpMethod.POST, "/");
	}

	@Test(expected = NoSuchRequestHandlingMethodException.class)
	public void viewErr() throws Exception {
		request(HttpMethod.GET, "/someOtherURI");
	}

	@Test
	public void viewLogin() throws Exception {
		request(HttpMethod.GET, "/login");
		ModelAndViewAssert.assertViewName(mav, "login/login");
	}
}
