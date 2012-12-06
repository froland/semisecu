package com.hermes.owasphotel.dao.base;

import java.io.Serializable;
import java.util.List;



/**
 * Base interface for DAO with CRUD operations.
 * @author k
 *
 * @param <I> The key type
 * @param <T> The identifiable type
 */
public interface SimpleDao<I extends Serializable, T extends Identifiable<I>> {
	/**
	 * Gets an object by its id.
	 * @param id The id
	 * @return The object or <code>null</code> if not found
	 */
	public T find(I id);
	
	/**
	 * Persists an object.
	 * @param obj The object
	 */
	public void save(T obj);
	
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
}
