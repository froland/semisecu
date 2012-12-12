package com.hermes.owasphotel.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelService;

/**
 * Service: Hotel
 * 
 * @author k
 */
@Service
@Transactional
public class HotelServiceImpl implements HotelService {
	@Autowired
	private HotelDao hotelDao;

	@Autowired
	private UserDao userDao;

	@Override
	public Hotel find(Integer id) {
		Hotel h = hotelDao.find(id);
		if (h == null)
			return null;
		// sort comments by sequence number (and load them inside the
		// transaction)
		Collections.sort(h.getComments(), new Comparator<Comment>() {
			@Override
			public int compare(Comment o1, Comment o2) {
				return o1.getSequence() - o2.getSequence();
			}
		});
		return h;
	}

	@Override
	public Hotel save(Hotel obj) {
		return hotelDao.save(obj);
	}

	@Override
	public void delete(Hotel obj) {
		hotelDao.delete(obj);
	}

	@Override
	public List<Hotel> findAll() {
		return hotelDao.findAll();
	}

	@Override
	public List<Hotel> findApproved() {
		return hotelDao.findApprovedHotels();
	}

	@Override
	public void approve(Hotel h) {
		h.approveHotel();
		h = hotelDao.save(h);
	}

	@Override
	public void addComment(Integer hotelId, String name, String text) {
		User user = userDao.find(name);
		Hotel hotel = find(hotelId);
		Comment c = new Comment(hotel, user);
		c.setText(text);
		hotel.getComments().add(c);
	}
}
