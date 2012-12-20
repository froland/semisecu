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

	private int enabled = 1;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "userid"), name = "ROLES")
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
		this.name = name;
	}

	public final void setPassword(String password, String oldPassword) {
		setPassword(password, oldPassword, true);
	}

	public void setPassword(String password, String oldPassword,
			boolean checkOld) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException(
					"The new password cannot be empty");
		Md5PasswordEncoder enc = new Md5PasswordEncoder();
		if (checkOld && !enc.isPasswordValid(this.password, oldPassword, null))
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
