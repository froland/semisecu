package com.hermes.owasphotel.dao.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.hermes.owasphotel.dao.base.Identifiable;

/**
 * An identifiable JPA entity.
 * 
 * @author k
 * 
 * @param <I>
 *            The type of the key
 */
@MappedSuperclass
public abstract class IdentifiableEntity<I extends Serializable> implements
		Identifiable<I> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private I id;

	@Override
	public final I getId() {
		return id;
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString() + "[id=" + getId() + "]";
	}
}
