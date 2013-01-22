package com.hermes.owasphotel.dao.jpa;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.hermes.owasphotel.dao.HotelDao;
import com.hermes.owasphotel.domain.Comment;
import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;

/**
 * DAO: Hotel
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
		return em
				.createQuery(
						"from Hotel where approved = :approved order by name",
						Hotel.class).setParameter("approved", approved)
				.getResultList();
	}

	@Override
	public Hotel getByName(String search) {
		return em
				.createQuery("from Hotel h where lower(h.name) = :t",
						Hotel.class).setParameter("t", search.toLowerCase())
				.getSingleResult();
	}

	@Override
	public List<Hotel> findSearchQuery(String search, boolean fullSearch,
			int maxResults) {
		/*
		 * Changed to case sensitive search in order to have insensitive search
		 * use toLowerCase on search and on h.name in JPQL.
		 */
		// build the like string
		String like = search + "%";
		if (fullSearch) {
			like = "%" + like;
		}

		// build the query
		/*
		 * Corrected code:
		 * 
		 * TypedQuery<Hotel> query = em .createQuery(
		 * "select h from Hotel h where h.approved is true and h.name like :t order by h.name"
		 * , Hotel.class).setParameter("t", like);
		 */
		String jpql = "select h from Hotel h where h.approved is true and h.name like '"
				+ like + "' order by h.name";
		TypedQuery<Hotel> query = em.createQuery(jpql, Hotel.class);
		if (maxResults > 0)
			query = query.setMaxResults(maxResults);
		return query.getResultList();
	}

	@Override
	public List<Hotel> findManagedHotels(User user) {
		return em
				.createQuery(
						"from Hotel h where h.manager=:user order by h.name",
						Hotel.class).setParameter("user", user).getResultList();
	}

	@Override
	public List<Hotel> findAll() {
		return em.createQuery("from Hotel h order by h.name", Hotel.class)
				.getResultList();
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment c = em.find(Comment.class, commentId);
		if (c == null)
			return;
		c.delete();
	}
}
