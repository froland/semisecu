package com.hermes.owasphotel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.hermes.owasphotel.dao.jpa.IdentifiableEntity;

@Entity
@Table(name = "users")
public class User extends IdentifiableEntity<Long> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private String email;

	@Column(name = "isadmin")
	private int isAdmin = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin > 0;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin ? 1 : 0;
	}
}
