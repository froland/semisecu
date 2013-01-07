package com.hermes.owasphotel.service.dto;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.hermes.owasphotel.domain.Identifiable;

public abstract class GenericDto<I extends Serializable, T extends Identifiable<I>> {
	private I id;

	public final I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

	protected Set<String> ignoredFields(boolean update) {
		HashSet<String> s = new HashSet<String>();
		if (update) {
			s.add("id");
		}
		return s;
	}

	public void update(T domain) {
		copyProperties(this, domain, ignoredFields(true));
	}

	public void read(T domain) {
		copyProperties(domain, this, ignoredFields(false));
	}

	protected static void copyProperties(Object source, Object destination,
			Set<String> exclude) {
		BeanWrapper in = new BeanWrapperImpl(source);
		BeanWrapper out = new BeanWrapperImpl(destination);
		if (exclude == null)
			exclude = Collections.emptySet();
		for (PropertyDescriptor d : in.getPropertyDescriptors()) {
			String name = d.getName();
			if (in.isReadableProperty(name) && out.isWritableProperty(name)
					&& !exclude.contains(name)) {
				out.setPropertyValue(name, in.getPropertyValue(name));
			}
		}
	}

	public T make() {
		throw new UnsupportedOperationException();
	}
}
