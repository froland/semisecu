package com.hermes.owasphotel.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/**
 * An identifiable JPA entity.
 * <p>The ID is generated using a sequence named "id_seq".</p>
 * @param <I> The type of the key
 * @see SequenceGenerator
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

	final void setId(I id) {
		this.id = id;
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
