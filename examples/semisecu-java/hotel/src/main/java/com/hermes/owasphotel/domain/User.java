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

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@Entity
@Table(name = "user")
@SequenceGenerator(name = "id_seq", sequenceName = "USERS_SEQ")
public class User extends IdentifiableEntity<Integer> {
	private static final long serialVersionUID = 1L;
	public static final String ROLE_USER = "user";
	public static final String ROLE_ADMIN = "admin";

	@Column(unique = true)
	private String name;
	private String password;
	private String email;

	private int enabled = 1;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "user_id"), name = "ROLE")
	@Column(name = "name")
	private List<String> roles = new ArrayList<String>();

	User() {
	}

	public User(String name, String password) {
		setName(name);
		roles.add(ROLE_USER);
		setPassword(password, null, false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("Empty name");
		this.name = name;
	}

	protected MessageDigestPasswordEncoder getPasswordEncoder() {
		return new Md5PasswordEncoder();
	}

	protected Object getSalt() {
		return null;
	}

	public boolean checkPassword(String password) {
		return getPasswordEncoder().isPasswordValid(this.password, password,
				getSalt());
	}

	public final void setPassword(String password, String oldPassword) {
		setPassword(password, oldPassword, true);
	}

	public void setPassword(String password, String oldPassword,
			boolean checkOld) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException(
					"The new password cannot be empty");
		MessageDigestPasswordEncoder enc = getPasswordEncoder();
		if (checkOld
				&& !enc.isPasswordValid(this.password, oldPassword, getSalt()))
			throw new IllegalArgumentException("The old password is not valid");
		this.password = enc.encodePassword(password, null);
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

	public List<String> getRoles() {
		return new ArrayList<String>(roles);
	}

}
