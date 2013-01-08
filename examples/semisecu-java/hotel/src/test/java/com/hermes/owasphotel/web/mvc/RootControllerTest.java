package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class RootControllerTest extends ControllerTestBase<RootController> {

	public RootControllerTest() {
		super(RootController.class);
	}

	@Test
	public void viewRoot() throws Exception {
		assertNotNull(controller.viewIndex());
	}

	@Test
	public void viewLogin() throws Exception {
		assertEquals("login/login", controller.viewLogin());
	}
}
