package com.hermes.owasphotel.service.dto;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Arrays;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.hermes.owasphotel.dao.base.Identifiable;

public abstract class GenericDto<I extends Serializable, T extends Identifiable<I>> {
	private I id;

	public final I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

	public void update(T domain) {
		copyProperties(this, domain, "id");
	}

	public void read(T domain) {
		copyProperties(domain, this);
	}

	protected static void copyProperties(Object source, Object destination,
			String... exclude) {
		BeanWrapper in = new BeanWrapperImpl(source);
		BeanWrapper out = new BeanWrapperImpl(destination);
		Arrays.sort(exclude);
		for (PropertyDescriptor d : in.getPropertyDescriptors()) {
			String name = d.getName();
			if (in.isReadableProperty(name) && out.isWritableProperty(name)
					&& Arrays.binarySearch(exclude, name) < 0) {
				out.setPropertyValue(name, in.getPropertyValue(name));
			}
		}
	}

	public T make() {
		throw new UnsupportedOperationException();
	}
}
