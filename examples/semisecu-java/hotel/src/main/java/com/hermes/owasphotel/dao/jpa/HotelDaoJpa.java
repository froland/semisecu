package com.hermes.owasphotel.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.domain.Hotel;

/**
 * DAO: Hotel
 * 
 * @author k
 */
@Repository("hotelDao")
public class HotelDaoJpa extends SimpleJPA<Integer, Hotel> implements HotelDao {
	/**
	 * Initializes this DAO.
	 */
	public HotelDaoJpa() {
		super(Hotel.class);
	}

	@Override
	public List<Hotel> findApprovedHotels() {
		return em.createQuery("from Hotel where approved != 0", Hotel.class)
				.getResultList();
	}

	@Override
	public void computeNote(Hotel h) {
		Double avg = em
				.createQuery(
						"select avg(n.note) from Comment n where n.hotel = :hotel",
						Double.class).setParameter("hotel", h)
				.getSingleResult();
		h.setAverageNote(avg);
	}

	@Override
	public List<Hotel> findTopNotedHotels(int count) {
		return em
				.createQuery(
						"select h from Hotel h left join h.comments n"
								+ " where h.approved != 0 group by h"
								+ " order by avg(n.note) desc", Hotel.class)
				.setMaxResults(count).getResultList();
	}

	@Override
	public List<Hotel> findSearchQuery(String search) {
		return em
				.createQuery(
						"select h from Hotel h where lower(h.hotelName) like :t",
						Hotel.class)
				.setParameter("t", "%" + search.toLowerCase() + "%")
				.getResultList();
	}

}
