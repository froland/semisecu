package com.hermes.owasphotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.HotelListItem;
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
	public void testListAll() {
		Hotel hotel = new Hotel("h", new User("a", "a"));
		ReflectionTestUtils.setField(hotel, "id", 1);
		Mockito.when(hotelDao.findAll()).thenReturn(Arrays.asList(hotel));

		List<HotelListItem> list = hotelService.listAll();

		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("h", list.get(0).getName());
	}

	@Test
	public void testListManaged() {
		User user = Mockito.mock(User.class);
		Mockito.when(userDao.getByName("k")).thenReturn(user);

		hotelService.listManagedHotels("k");

		Mockito.verify(hotelDao).findManagedHotels(user);
	}

	@Test
	public void testApprove() {
		Hotel hotel = Mockito.mock(Hotel.class);
		Mockito.when(hotelDao.getById(1)).thenReturn(hotel);

		assertSame(hotel, hotelService.approve(1));
		Mockito.verify(hotel).approveHotel();
	}

	@Test
	public void testAddComment() {
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
	public void testDeleteComment() {
		int seq = 8;
		Hotel hotel = Mockito.mock(Hotel.class);
		Mockito.when(hotelDao.getById(1)).thenReturn(hotel);
		Comment comment = Mockito.mock(Comment.class);
		Mockito.when(comment.getSequence()).thenReturn(seq);
		Mockito.when(hotel.getComments()).thenReturn(
				Collections.unmodifiableList(Arrays.asList(comment)));

		hotelService.deleteComment(1, seq);

		Mockito.verify(comment).delete();
	}
}
