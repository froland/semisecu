package com.hermes.owasphotel.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
	private I id;

	@Override
	public final I getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return id == null ? super.hashCode() : id.hashCode()
				* getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (id == null) {
			return super.equals(obj);
		} else if (getClass().isInstance(obj)) {
			return id.equals(((Identifiable<?>) obj).getId());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return super.toString() + "[id=" + getId() + "]";
	}
}
