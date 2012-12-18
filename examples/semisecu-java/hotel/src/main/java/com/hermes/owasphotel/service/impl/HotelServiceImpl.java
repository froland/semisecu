package com.hermes.owasphotel.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
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
	static final Logger log = Logger.getLogger(HotelServiceImpl.class);

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
	public List<Hotel> findSearchQuery(String search) {
		return hotelDao.findSearchQuery(search);
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
		Hotel hotel = hotelDao.find(hotelId);
		Comment c = hotel.addComment(user);
		if (user == null) {
			c.setUserName(name);
		}
		c.setText(text);
		c.setNote(note);
	}

	@Override
	public void deleteComment(Integer hotelId, int commentSeq) {
		Hotel hotel = hotelDao.find(hotelId);
		Comment comment = null;
		try {
			comment = hotel.getComments().get(commentSeq - 1);
			if (comment.getSequence() != commentSeq) {
				// not 1-indexed
				comment = null;
			}
		} catch (IndexOutOfBoundsException e) {
			comment = null;
		}
		if (comment == null) {
			for (Comment c : hotel.getComments()) {
				if (c.getSequence() == commentSeq) {
					comment = c;
					break;
				}
			}
		}

		// mark as deleted
		comment.delete();
	}

	@Override
	public byte[] getHotelImage(Integer hotelId) {
		Hotel hotel = hotelDao.find(hotelId);
		if (hotel == null)
			return null;
		return hotel.getImage();
	}

	@Override
	public void setHotelImage(Integer hotelId, byte[] image) {
		hotelDao.find(hotelId).setImage(image);
	}
}
