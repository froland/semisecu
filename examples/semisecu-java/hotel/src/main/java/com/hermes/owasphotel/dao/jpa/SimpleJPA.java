package com.hermes.owasphotel.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hermes.owasphotel.dao.base.SimpleDao;
import com.hermes.owasphotel.domain.Identifiable;

/**
 * Template class for CRUD operations that uses JPA.
 * 
 * @author k
 * 
 * @param <I>
 *            The type of the key
 * @param <T>
 *            The type of the object
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

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public T getById(I id) {
		return em.find(type, id);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void save(T obj) {
		em.persist(obj);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public T merge(T obj) {
		return em.merge(obj);
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void delete(T obj) {
		em.remove(obj);
	}

	@Override
	public List<T> findAll() {
		return em.createQuery("from " + type.getSimpleName(), type)
				.getResultList();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void flush() {
		em.flush();
	}

	@Transactional(propagation = Propagation.MANDATORY)
	@Override
	public void clear() {
		em.clear();
	}
}
