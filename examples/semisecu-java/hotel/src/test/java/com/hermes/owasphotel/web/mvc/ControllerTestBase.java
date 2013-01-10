package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Map;
import java.util.TreeMap;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.hermes.owasphotel.domain.Role;

public abstract class ControllerTestBase<T> {

	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected T controller;
	private final Class<T> controllerClass;

	public ControllerTestBase(Class<T> c) {
		this.controllerClass = c;
	}

	@Before
	public void initMockHttp() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}

	@Before
	public void initController() throws Exception {
		controller = controllerClass.newInstance();
	}

	protected Model createModel() {
		return new ExtendedModelMap();
	}

	protected Authentication createAuthentication(Object principal,
			Role... roles) {
		String[] roles_str = new String[roles.length];
		for (int i = 0; i < roles.length; i++) {
			roles_str[i] = roles[i].toString();
		}
		return new UsernamePasswordAuthenticationToken(principal, null,
				AuthorityUtils.createAuthorityList(roles_str));
	}

	protected BindingResult createBindingResult(String objectName) {
		return new MapBindingResult(new TreeMap<String, Object>(), objectName);
	}

	protected RedirectAttributes createRedirectAttributes() {
		return new RedirectAttributesModelMap();
	}

	public static <T> T assertType(Object obj, Class<T> type) {
		assertThat(obj, CoreMatchers.instanceOf(type));
		return type.cast(obj);
	}

	public static <T> T assertType(Map<String, ?> model, String name,
			Class<T> type) {
		Object obj = model.get(name);
		assertNotNull("Object not found in model: " + name + "[ model: "
				+ model + "]", obj);
		return assertType(obj, type);
	}

	public static void assertRedirect(Object result) {
		if (result instanceof CharSequence) {
			if (result.toString().startsWith("redirect:"))
				return;
		}
		fail("The result is not a redirect: " + result);
	}
}
