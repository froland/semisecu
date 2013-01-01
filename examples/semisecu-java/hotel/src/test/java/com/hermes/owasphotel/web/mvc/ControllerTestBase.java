package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.servlet.ServletContext;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public abstract class ControllerTestBase<T> {
	/*
	 * TODO These tests should be rewritten using MockMvc once Spring 3.2 is
	 * released. For instance, view resolvers and security checks are not
	 * tested.
	 */

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private HandlerAdapter testHandlerAdapter = new AnnotationMethodHandlerAdapter();

	protected MockHttpServletRequest request;
	protected MockHttpServletResponse response;
	protected ModelAndView mav;
	protected T controller;
	private final Class<T> controllerClass;

	public ControllerTestBase(Class<T> c) {
		this.controllerClass = c;
	}

	@Before
	public void initMockHttp() throws InstantiationException,
			IllegalAccessException {
		request = new MockHttpServletRequest();
		request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
		response = null;
		mav = null;

		// create the controller
		if (applicationContext != null) {
			controller = applicationContext.getBean(controllerClass);
		} else {
			controller = controllerClass.newInstance();
		}
	}

	public Authentication authentify(Object principal, Object credentials,
			String... roles) {
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				principal, credentials,
				AuthorityUtils.createAuthorityList(roles));
		request.setUserPrincipal(auth);
		SecurityContextHolder.getContext().setAuthentication(auth);
		return auth;
	}

	public void request(HttpMethod method, String uri) throws Exception {
		request.setMethod(method.toString());
		request.setRequestURI(uri);
		if (response != null)
			throw new IllegalStateException("Call request() only once per test");
		response = new MockHttpServletResponse();
		mav = testHandlerAdapter.handle(request, response, controller);
	}

	public static <T> T assertType(Object obj, Class<T> type) {
		assertThat(obj, CoreMatchers.instanceOf(type));
		return type.cast(obj);
	}

	public static <T> T assertType(ModelAndView mav, String name, Class<T> type) {
		Object obj = mav.getModel().get(name);
		assertNotNull(
				"Object not found in model: " + name + " [model: "
						+ mav.getModel() + "]", obj);
		return assertType(obj, type);
	}

	public static void assertNoBindingErrors(ModelAndView mav) {
		for (Object v : mav.getModel().values()) {
			if (v instanceof BindingResult) {
				BindingResult r = (BindingResult) v;
				assertFalse("Found binding errors in " + r, r.hasErrors());
			}
		}
	}

	public static BindingResult assertBindingResult(ModelAndView mav) {
		for (Object v : mav.getModel().values()) {
			if (v instanceof BindingResult) {
				return (BindingResult) v;
			}
		}
		fail("No BindingResult found in model: " + mav);
		return null;
	}

	public static void assertRedirect(ModelAndView mav) {
		String name = mav.getViewName();
		assertTrue("Expecting a redirect",
				name != null && name.startsWith("redirect:"));
	}

	public void assertResponse(HttpStatus st) {
		assertEquals(st, HttpStatus.valueOf(response.getStatus()));
	}

	@Configuration
	@EnableWebMvc
	public static class WebConfiguration extends WebMvcConfigurationSupport {
		public WebConfiguration() {
			setServletContext(new MockServletContext());
		}

		public WebConfiguration(ServletContext ctx) {
			if (ctx != null) {
				setServletContext(ctx);
			}
		}

		@Override
		protected void configureMessageConverters(
				List<HttpMessageConverter<?>> converters) {
			converters.add(new MappingJacksonHttpMessageConverter());
			addDefaultHttpMessageConverters(converters);
		}

		@Bean
		public HandlerAdapter testHandlerAdapter() {
			ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
			webBindingInitializer.setConversionService(mvcConversionService());
			webBindingInitializer.setValidator(mvcValidator());

			AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
			adapter.setMessageConverters(getMessageConverters().toArray(
					new HttpMessageConverter[0]));
			adapter.setWebBindingInitializer(webBindingInitializer);
			return adapter;
		}
	}
}
