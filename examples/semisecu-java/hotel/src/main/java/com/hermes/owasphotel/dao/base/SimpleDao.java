package com.hermes.owasphotel.dao.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.NoResultException;

import com.hermes.owasphotel.domain.Identifiable;

/**
 * Base interface for DAO with CRUD operations.
 *
 * @param <I> The key type
 * @param <T> The identifiable type
 */
public interface SimpleDao<I extends Serializable, T extends Identifiable<I>> {
	/**
	 * Gets an object by its id.
	 * @param id The id
	 * @return The object
	 * @throws NoResultException when the object was not found
	 */
	public T getById(I id) throws NoResultException;

	/**
	 * Persists an object.
	 * @param obj The object
	 */
	public void save(T obj);

	/**
	 * Merges an object.
	 * @param obj The object
	 * @return The saved object
	 */
	public T merge(T obj);

	/**
	 * Deletes the object.
	 * @param obj The object
	 */
	public void delete(T obj);

	/**
	 * Finds all objects.
	 * @return A list of objects
	 */
	public List<T> findAll();

	/**
	 * Flushes the modifications.
	 * <p>After this call the inserted objects will have their ids.</p>
	 */
	public void flush();

	/**
	 * Invalidates caches.
	 */
	public void clear();
}
