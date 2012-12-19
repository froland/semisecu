package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hermes.owasphotel.dao.jpa.IdentifiableEntity;

@Entity
@Table(name = "users")
@SequenceGenerator(name = "id_seq", sequenceName = "USERS_SEQ")
public class User extends IdentifiableEntity<Integer> {
	private static final long serialVersionUID = 1L;
	private static final String ROLE_USER = "user";
	private static final String ROLE_ADMIN = "admin";

	@Column(unique = true)
	private String name;
	private String password;
	private String email;

	private int enabled;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "userid"), name = "ROLES")
	@Column(name = "name")
	private List<String> roles = new ArrayList<String>();

	User() {
	}

	public User(String name) {
		setName(name);
		roles.add(ROLE_USER);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled != 0;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled ? 1 : 0;
	}

	public boolean isAdmin() {
		return roles.contains(ROLE_ADMIN);
	}

	public void setAdmin(boolean isAdmin) {
		if (isAdmin) {
			if (!roles.contains(ROLE_ADMIN))
				roles.add(ROLE_ADMIN);
		} else {
			roles.remove(ROLE_ADMIN);
		}
	}
}
