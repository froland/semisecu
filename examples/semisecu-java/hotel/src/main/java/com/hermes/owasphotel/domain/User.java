package com.hermes.owasphotel.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@Entity
@Table(name = "user")
@SequenceGenerator(name = "id_seq", sequenceName = "USERS_SEQ")
public class User extends IdentifiableEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String name;
	private String password;
	private String email;

	@Column(name = "enabled", columnDefinition = "tinyint default true")
	private boolean enabled = true;

	@ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
	@CollectionTable(joinColumns = @JoinColumn(name = "user_id"), name = "ROLE")
	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private Set<Role> roles = new HashSet<Role>();

	User() {
	}

	public User(String name, String password) {
		setName(name);
		roles.add(Role.USER);
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
		return enabled;
	}

	public void enable() {
		this.enabled = true;
	}

	public void disable() {
		this.enabled = false;
	}

	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	public void setAdmin(boolean isAdmin) {
		if (isAdmin) {
			roles.add(Role.ADMIN);
		} else {
			roles.remove(Role.ADMIN);
		}
	}

	public List<Role> getRoles() {
		return new ArrayList<Role>(roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return ObjectUtils.equals(getName(), other.getName());
	}

	@Override
	public int hashCode() {
		return super.hashCode() + ObjectUtils.hashCode(getName());
	}

}
