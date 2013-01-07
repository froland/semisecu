package com.hermes.owasphotel.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.ModelAndViewAssert;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.HotelForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HotelControllerTest.Config.class)
public class HotelControllerTest extends ControllerTestBase<HotelController> {
	public HotelControllerTest() {
		super(HotelController.class);
	}

	@Test
	public void viewHotels() throws Exception {
		request(HttpMethod.GET, "/hotel");
		ModelAndViewAssert.assertViewName(mav, "hotel/list");
		assertType(mav, "hotels", List.class);
	}

	@Test
	public void viewHotel() throws Exception {
		request(HttpMethod.GET, "/hotel/1");
		ModelAndViewAssert.assertViewName(mav, "hotel/view");
		assertType(mav, "hotel", Hotel.class);
	}

	@Test
	public void postComment() throws Exception {
		request.addParameter("text", "hello");
		request.addParameter("name", "me");
		request.addParameter("note", "8");
		request(HttpMethod.POST, "/hotel/1/comment");
		assertResponse(HttpStatus.OK);
		assertNoBindingErrors(mav);
	}

	@Test
	public void viewUpdateHotel() throws Exception {
		authentify("manager", null, "user");
		request(HttpMethod.GET, "/hotel/1/update");
		assertResponse(HttpStatus.OK);
		assertType(mav, "hotel", HotelForm.class);
	}

	@Test
	public void postUpdateHotel() throws Exception {
		authentify("manager", null, "user");
		request.setParameter("address", "updateAddress");
		request.setParameter("hotelName", "updateName");
		request.setParameter("stars", "3");
		request(HttpMethod.POST, "/hotel/1/update");
		assertNoBindingErrors(mav);
		assertRedirect(mav);
	}

	@Test
	public void postApprove() throws Exception {
		request(HttpMethod.POST, "/hotel/1/approve");
		assertNoBindingErrors(mav);
		assertRedirect(mav);
	}

	@Configuration
	@Import(ControllerTestBase.WebConfiguration.class)
	public static class Config {
		static final User manager = new User("manager", "m");

		@Bean
		public HotelController hotelController() {
			return new HotelController();
		}

		@Bean
		public UserService userService() {
			UserService service = Mockito.mock(UserService.class);
			Mockito.when(service.find("a")).thenReturn(new User("a", "a"));
			Mockito.when(service.find(manager.getName())).thenReturn(manager);
			return service;
		}

		@Bean
		public HotelService hotelService() {
			HotelService service = Mockito.mock(HotelService.class);
			Hotel h = new Hotel("h", manager);
			ReflectionTestUtils.setField(h, "id", 1);
			assert h.getId() != null : "h.id not set";
			Mockito.when(service.find(h.getId())).thenReturn(h);
			Mockito.when(service.findByName(h.getName())).thenReturn(h);
			// TODO
//			Mockito.when(
//					service.update(Mockito.eq(h.getId()),
//							Mockito.any(HotelForm.class))).thenReturn(h);

			return service;
		}

		@Bean
		public HttpServletRequest servletRequest() {
			return new MockHttpServletRequest();
		}
	}
}
