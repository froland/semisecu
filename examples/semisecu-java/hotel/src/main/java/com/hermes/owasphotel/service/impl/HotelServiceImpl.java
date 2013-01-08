package com.hermes.owasphotel.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.dao.UserDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.HotelListItem;
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
	private static final Logger logger = LoggerFactory
			.getLogger(HotelServiceImpl.class);

	@Autowired
	private HotelDao hotelDao;

	@Autowired
	private UserDao userDao;

	public HotelDao getHotelDao() {
		return hotelDao;
	}

	public void setHotelDao(HotelDao hotelDao) {
		this.hotelDao = hotelDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Hotel getById(Integer id) {
		Hotel h = hotelDao.getById(id);
		if (h == null)
			return null;
		// load comments
		h.getComments().size();

		return h;
	}

	@Override
	public void save(Hotel obj) {
		hotelDao.save(obj);
	}

	@Override
	public void delete(Hotel obj) {
		hotelDao.delete(obj);
	}

	@Override
	public List<HotelListItem> listAll() {
		return itemize(hotelDao.findAll());
	}

	private List<HotelListItem> itemize(List<Hotel> lh) {
		List<HotelListItem> result = new LinkedList<HotelListItem>();
		for (Hotel hotel : lh) {
			result.add(new HotelListItem(hotel.getId(), hotel.getName(), hotel
					.getNbComments(false), hotel.getAverageNote()));
		}
		return result;
	}

	@Override
	public List<HotelListItem> listApproved() {
		return itemize(hotelDao.findApprovedHotels(true));
	}

	@Override
	public List<HotelListItem> listNotApproved() {
		return itemize(hotelDao.findApprovedHotels(false));
	}

	@Override
	public List<HotelListItem> listTopNoted(int count) {
		List<Hotel> lh = hotelDao.findApprovedHotels(true);
		Collections.sort(lh, new HotelAverageNoteComparator());	
		if(lh.size() > count)
			return itemize(lh.subList(0, count));
		return itemize(lh);
	}

	@Override
	public Hotel getByName(String search) {
		return hotelDao.getByName(search);
	}

	@Override
	public List<String> findForAutoComplete(String query) {
		List<String> list = new ArrayList<String>();
		for (Hotel h : hotelDao.findSearchQuery(query, false, 100)) {
			list.add(h.getName());
		}
		return list;
	}

	@Override
	public List<HotelListItem> listSearchQuery(String search) {
		return itemize(hotelDao.findSearchQuery(search, true, 0));
	}

	@Override
	public List<HotelListItem> listManagedHotels(String name) {
		User user = userDao.getByName(name);
		return itemize(hotelDao.findManagedHotels(user));
	}

	@Override
	public Hotel update(Hotel hotel) {
		return hotelDao.merge(hotel);
	}

	@Override
	public Hotel approve(Integer hotelId) {
		Hotel h = hotelDao.getById(hotelId);
		if (h == null)
			throw new IllegalArgumentException("Hotel does not exist: id="
					+ hotelId);
		h.approveHotel();
		logger.info("Hotel approved: " + h);
		return h;
	}

	@Override
	public void addComment(Integer hotelId, String name, int note, String text) {
		User user = null;
		if (name != null) {
			user = userDao.getByName(name);
		}
		Hotel hotel = hotelDao.getById(hotelId);
		hotel.createComment(user, note, text);
	}

	@Override
	public void deleteComment(Integer hotelId, int commentSeq) {
		Hotel hotel = hotelDao.getById(hotelId);
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
		if (comment != null) {
			comment.delete();
		}
	}

	@Override
	public byte[] getHotelImage(Integer hotelId) {
		Hotel hotel = hotelDao.getById(hotelId);
		if (hotel == null)
			return null;
		return hotel.getImage();
	}

	@Override
	public void setHotelImage(Integer hotelId, byte[] image) {
		hotelDao.getById(hotelId).setImage(image);
	}
}
