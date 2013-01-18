package com.hermes.owasphotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.impl.HotelServiceImpl;

public class HotelServiceTest {
	private HotelService hotelService;

	private HotelDao hotelDao;
	private UserDao userDao;

	@Before
	public void initService() {
		HotelServiceImpl service = new HotelServiceImpl();
		this.hotelService = service;
		service.setHotelDao(hotelDao = Mockito.mock(HotelDao.class));
		service.setUserDao(userDao = Mockito.mock(UserDao.class));
	}

	@Test
	public void listAllItems() {
		Hotel hotel = new Hotel("h", new User("a", "a"));
		// set id as HotelListItem have an `int id`
		ReflectionTestUtils.setField(hotel, "id", 1);
		Mockito.when(hotelDao.findAll()).thenReturn(Arrays.asList(hotel));

		List<HotelListItem> list = hotelService.listAll();

		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("h", list.get(0).getName());
	}

	@Test
	public void listManaged() {
		User user = Mockito.mock(User.class);
		Mockito.when(userDao.getByName("k")).thenReturn(user);

		hotelService.listManagedHotels("k");

		Mockito.verify(hotelDao).findManagedHotels(user);
	}

	@Test
	public void testApprove() {
		Hotel hotel = new Hotel("h", new User("a", "a"));
		assertFalse("Hotel is approved by default", hotel.isApproved());
		Mockito.when(hotelDao.getById(1)).thenReturn(hotel);

		assertSame(hotel, hotelService.approve(1));
		assertTrue("Hotel was not approved", hotel.isApproved());
	}

	@Test
	public void addComment() {
		String username = "k";
		User user = Mockito.mock(User.class);
		int note = 5;
		String text = "hello world";
		Mockito.when(userDao.getByName(username)).thenReturn(user);
		Hotel hotel = Mockito.mock(Hotel.class);
		Mockito.when(hotelDao.getById(1)).thenReturn(hotel);

		hotelService.addComment(1, username, note, text);

		Mockito.verify(hotel).createComment(user, note, text);
	}

	@Test
	public void deleteComment() {
		Hotel hotel = new Hotel("h", new User("a", "a"));
		Mockito.when(hotelDao.getById(1)).thenReturn(hotel);
		Comment comment = hotel.createComment(Mockito.mock(User.class), 5,
				"hello world");

		// set the id of the comment to delete
		// (usually set when the transaction ends)
		final int seq = 8;
		ReflectionTestUtils.setField(comment, "id", seq);

		hotelService.deleteComment(1, seq);

		assertTrue("Comment not deleted", comment.isDeleted());
	}
}
