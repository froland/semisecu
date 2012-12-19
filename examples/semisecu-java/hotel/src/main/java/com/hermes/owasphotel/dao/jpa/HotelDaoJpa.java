package com.hermes.owasphotel.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

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
	public List<Hotel> findApprovedHotels(boolean approved) {
		StringBuilder query = new StringBuilder("from Hotel where approved");
		query.append(' ').append(approved ? "!=" : "=").append(" 0");
		return em.createQuery(query.toString(), Hotel.class).getResultList();
	}

	@Override
	public void computeNote(Hotel h) {
		Double avg = em
				.createQuery(
						"select avg(n.note) from Comment n where n.hotel = :hotel and n.deleted = 0",
						Double.class).setParameter("hotel", h)
				.getSingleResult();
		h.setAverageNote(avg);
	}

	@Override
	public Long countComments(Hotel h) {
		return em
				.createQuery(
						"select count(*) from Comment n where n.hotel = :hotel and n.deleted = 0",
						Long.class).setParameter("hotel", h).getSingleResult();
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
	public Hotel findByName(String search) {
		List<Hotel> list = em
				.createQuery("from Hotel h where lower(h.hotelName) = :t",
						Hotel.class).setParameter("t", search.toLowerCase())
				.getResultList();
		return list.size() == 1 ? list.get(0) : null;
	}

	@Override
	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults) {
		// build the like string
		String like = search.toLowerCase() + "%";
		if (fullSearch) {
			like = "%" + like;
		}
		// build the query
		TypedQuery<Hotel> query = em.createQuery(
				"select h from Hotel h where lower(h.hotelName) like :t",
				Hotel.class).setParameter("t", like);
		if (maxResults > 0)
			query = query.setMaxResults(maxResults);
		return query.getResultList();
	}

}
