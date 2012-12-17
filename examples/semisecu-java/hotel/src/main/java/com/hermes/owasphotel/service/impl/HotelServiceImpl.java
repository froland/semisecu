package com.hermes.owasphotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelDto;
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
		// load comments
		h.getComments().size();
		// set the note
		hotelDao.computeNote(h);

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
	public List<Hotel> findTopNoted(int count) {
		return hotelDao.findTopNotedHotels(count);
	}

	@Override
	public Hotel update(Integer hotelId, HotelDto data) {
		Hotel h = hotelDao.find(hotelId);
		if (h == null)
			return null;
		data.update(h);
		return h;
	}

	@Override
	public void approve(Hotel h) {
		h.approveHotel();
		hotelDao.save(h);
	}

	@Override
	public void addComment(Integer hotelId, String name,
			boolean authentifiedUser, int note, String text) {
		User user = null;
		if (authentifiedUser) {
			user = userDao.find(name);
		}
		Hotel hotel = find(hotelId);
		Comment c = hotel.addComment(user);
		if (user == null) {
			c.setUserName(name);
		}
		c.setText(text);
		c.setNote(note);
	}
}
