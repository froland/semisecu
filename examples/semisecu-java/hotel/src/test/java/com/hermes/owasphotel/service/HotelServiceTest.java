package com.hermes.owasphotel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.HotelListItem;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.web.mvc.form.HotelForm;

public class HotelServiceTest extends ServiceTestBase {
	@Autowired
	private HotelService hotelService;

	@Autowired
	private UserService userService;

	private User manager;
	private Hotel a;
	private Hotel b;

	@Before
	public void initializeHotels() {
		manager = new User("u", "u");
		userService.save(manager);
		a = new Hotel("a", manager);
		b = new Hotel("b", manager);
		a.approveHotel();
		b.approveHotel();
		a.createComment(null, 2, "default");
		hotelService.save(a);
		hotelService.save(b);
		// another hotel
		hotelService.save(new Hotel("c", manager));
	}

	@Test
	public void testFind() {
		Hotel h = hotelService.getById(a.getId());
		assertNotNull("Hotel not found (initialization error)", h);
		assertNotNull("Comments not retrieved", h.getComments());
		assertNotNull("Average not not computed by find()", h.getAverageNote());
	}

	@Test
	public void testSave() {
		User a = new User("a", "a");
		userService.save(a);
		Hotel h = new Hotel("tested", a);
		hotelService.save(h);
		assertNotNull("Not saved", h.getId());
		h = hotelService.getById(h.getId());
		assertEquals("Invalid name", "tested", h.getName());
	}

	@Test
	public void testListHotels() {
		List<HotelListItem> listAll = hotelService.listAll();
		assertFalse("No hotels initialized", listAll.isEmpty());
		List<HotelListItem> listApproved = hotelService.listApproved();
		List<HotelListItem> listNA = hotelService.listNotApproved();
		assertEquals("Hotels are approved or not approved", listAll.size(),
				listApproved.size() + listNA.size());
		for (HotelListItem item : listAll) {
			assertNotNull(item.getHotelName());
			assertNotNull(item.getNbComments());
		}
	}

	@Test
	public void testApprove() {
		HotelListItem h = hotelService.listNotApproved().get(0);
		hotelService.approve(h.getId());
		for (HotelListItem i : hotelService.listNotApproved()) {
			if (i.getId() == h.getId())
				fail("Hotel failed to be approved");
		}
	}

	@Test
	public void testAddComment() {
		hotelService.addComment(a.getId(), null, 2, "hello world!");
		hotelService
				.addComment(a.getId(), manager.getName(), 5, "manager word");
		a = hotelService.getById(a.getId());

		int found = 0;
		for (Comment c : a.getComments()) {
			if ("hello world!".equals(c.getText())) {
				found++;
				assertEquals("comment note", 2, c.getNote());
				assertEquals("comment user name", "chris", c.getUserName());
				assertNull("comment user", c.getUser());
				assertNotNull("comment date", c.getDate());
				assertEquals("comment hotel", a.getId(), c.getHotel().getId());
			} else if ("manager word".equals(c.getText())) {
				found++;
				assertEquals("comment manager name", manager.getName(),
						c.getUserName());
				assertNotNull("comment manager user", c.getUser());
			}
		}
		assertEquals("Found invalid number of comments", 2, found);
	}

	@Test
	public void testDeleteComment() {
		final int size = a.getNbComments(false);
		assert size > 0;
		Comment toDelete = a.getComments().get(0);
		hotelService.deleteComment(a.getId(), toDelete.getSequence());
		a = hotelService.getById(a.getId());
		assertEquals("Comment not marked as deleted", size - 1,
				a.getNbComments(false));
	}

	@Test
	public void testUpdateHotel() {
		HotelForm dto = new HotelForm(a);
		dto.setAddress("my new address");
		final byte[] imgData = new byte[2];
		CommonsMultipartFile file = Mockito.mock(CommonsMultipartFile.class);
		Mockito.when(file.getBytes()).thenReturn(imgData);
		Mockito.when(file.getSize()).thenReturn((long) imgData.length);
		dto.setFile(file);
		Hotel h = hotelService.getById(a.getId());
		dto.update(h, userService);
		h = hotelService.update(h);

		assertEquals("Address not updated", "my new address", h.getAddress());
		assertEquals("Image not updated", imgData, h.getImage());
	}
}
