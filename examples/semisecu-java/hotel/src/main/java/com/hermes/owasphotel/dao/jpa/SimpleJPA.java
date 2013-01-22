package com.hermes.owasphotel.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Identifiable;

/**
 * Template class for CRUD operations that uses JPA.
 * 
 * @param <I> The type of the key
 * @param <T> The type of the object
 * @see SimpleDao
 */
abstract class SimpleJPA<I extends Serializable, T extends Identifiable<I>>
		implements SimpleDao<I, T> {

	private final Class<T> type;

	/**
	 * The entity manager.
	 */
	@PersistenceContext
	protected EntityManager em;

	/**
	 * Initializes this DAO.
	 * 
	 * @param type
	 *            The type of the class
	 */
	public SimpleJPA(Class<T> type) {
		assert type != null;
		this.type = type;
	}

	@Override
	public T getById(I id) {
		T obj = em.find(type, id);
		if (obj == null)
			throw new NoResultException("No "
					+ type.getSimpleName().toLowerCase() + " with id: " + id);
		return obj;
	}

	@Override
	public void save(T obj) {
		em.persist(obj);
	}

	@Override
	public T merge(T obj) {
		return em.merge(obj);
	}

	@Override
	public void delete(T obj) {
		em.remove(obj);
	}

	@Override
	public List<T> findAll() {
		return em.createQuery("from " + type.getSimpleName(), type)
				.getResultList();
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public void clear() {
		em.clear();
	}
}
