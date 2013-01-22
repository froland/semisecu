package com.hermes.owasphotel.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.ObjectUtils;

/**
 * A postal address.
 */
@Embeddable
@Access(AccessType.PROPERTY)
public final class Address implements Serializable {
	private static final long serialVersionUID = -5781461909685717465L;

	private String street;
	private String number;
	private String city;
	private String zipCode;
	private String country;

	// default setters added for hibernate

	Address() {
	}

	/**
	 * Creates a new address.
	 * @param street The street
	 * @param number The number
	 * @param city The city name
	 * @param zipCode The ZIP code
	 * @param country The country
	 */
	public Address(String street, String number, String city, String zipCode,
			String country) {
		this.street = street;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	void setCountry(String country) {
		this.country = country;
	}

	@Override
	public final boolean equals(Object obj) {
		return obj instanceof Address && equals((Address) obj);
	}

	/**
	 * Tests whether the addresses are equal.
	 * @param address The other address
	 * @return true if the attribute values are the same
	 */
	public boolean equals(Address address) {
		return address != null
				&& ObjectUtils.equals(getCity(), address.getCity())
				&& ObjectUtils.equals(getCountry(), address.getCountry())
				&& ObjectUtils.equals(getNumber(), address.getNumber())
				&& ObjectUtils.equals(getStreet(), address.getStreet())
				&& ObjectUtils.equals(getZipCode(), address.getZipCode());
	}

	@Override
	public int hashCode() {
		return ObjectUtils.hashCodeMulti(getCity(), getCountry(), getNumber(),
				getStreet(), getZipCode());
	}

	@Override
	public String toString() {
		return "Address[\n" + getStreet() + " " + getNumber() + "\n"
				+ getZipCode() + " " + getCity() + "\n" + getCountry() + "]";
	}

}
