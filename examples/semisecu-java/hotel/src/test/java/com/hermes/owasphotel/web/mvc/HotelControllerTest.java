package com.hermes.owasphotel.web.mvc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.HotelForm;

public class HotelControllerTest extends ControllerTestBase<HotelController> {

	private HotelService hotelService;
	private UserService userService;

	public HotelControllerTest() {
		super(HotelController.class);
	}

	@Override
	@Before
	public void initController() throws Exception {
		super.initController();
		controller.setHotelService(hotelService = Mockito
				.mock(HotelService.class));
		controller
				.setUserService(userService = Mockito.mock(UserService.class));
		controller.setRequest(request);
	}

	@Test
	public void viewHotels() throws Exception {
		Model model = createModel();

		assertEquals("hotel/list", controller.viewHotels(model, 0));
		assertType(model.asMap(), "hotels", List.class);
	}

	@Test
	public void viewHotel() throws Exception {
		Model model = createModel();
		Mockito.when(hotelService.getById(1)).thenReturn(
				Mockito.mock(Hotel.class));

		assertEquals("hotel/view", controller.viewHotel(model, 1));
		assertType(model.asMap(), "hotel", Hotel.class);
	}

	@Test
	public void postComment() throws Exception {
		Authentication auth = null;

		assertRedirect(controller.addComment(1, auth, "hello world", 5));
		Mockito.verify(hotelService).addComment(1, null, 5, "hello world");
	}

	private Hotel createTestHotel() {
		User manager = new User("manager", "m");
		Mockito.when(userService.getByName("manager")).thenReturn(manager);
		Hotel h = new Hotel("test", manager);
		return h;
	}

	@Test
	public void viewUpdateHotel() throws Exception {
		Model model = createModel();
		Authentication auth = createAuthentication("manager", Role.USER);
		Hotel hotel = createTestHotel();
		Mockito.when(hotelService.getById(1)).thenReturn(hotel);

		assertNotNull(controller.viewUpdateHotel(model, auth, 1));
		assertType(model.asMap(), "hotel", HotelForm.class);
	}

	@Test
	public void postUpdateHotel() throws Exception {
		Model model = createModel();
		Authentication auth = createAuthentication("manager", Role.USER);
		Hotel hotel = createTestHotel();
		ReflectionTestUtils.setField(hotel, "id", 1);
		Mockito.when(hotelService.getById(1)).thenReturn(hotel);
		Mockito.when(hotelService.update(hotel)).thenReturn(hotel);
		HotelForm form = new HotelForm(hotel);

		assertRedirect(controller.updateHotel(model, auth, 1, form,
				createBindingResult("hotel")));
		Mockito.verify(hotelService).update(Mockito.any(Hotel.class));
	}

	@Test
	public void postApprove() throws Exception {
		assertRedirect(controller.approveHotel(1));
		Mockito.verify(hotelService).approve(1);
	}
}
