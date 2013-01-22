package com.hermes.owasphotel.domain;

import java.util.HashSet;
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

/**
 * A user.
 */
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "user_id"), name = "ROLE")
	@Enumerated(EnumType.STRING)
	@Column(name = "name")
	private Set<Role> roles = new HashSet<Role>();

	User() {
	}

	/**
	 * Creates a new user.
	 * @param name The name of the user
	 * @param password The password
	 * @see #setName(String)
	 * @see #setPassword(String, String, boolean)
	 */
	public User(String name, String password) {
		setName(name);
		roles.add(Role.USER);
		setPassword(password, null, false);
	}

	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the user.
	 * @param name The new name
	 * @throws IllegalArgumentException when name is empty
	 */
	public void setName(String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("Empty name");
		this.name = name;
	}

	/**
	 * Gets the password encoder.
	 * @return The password encoder
	 */
	protected MessageDigestPasswordEncoder getPasswordEncoder() {
		return new Md5PasswordEncoder();
	}

	/**
	 * Gets the salt.
	 * @return The salt
	 */
	protected Object getSalt() {
		return null;
	}

	/**
	 * Checks a given password.
	 * @param password The password to check
	 * @return true iff the password matches
	 */
	public boolean checkPassword(String password) {
		return getPasswordEncoder().isPasswordValid(this.password, password,
				getSalt());
	}

	/**
	 * Sets a password.
	 * <p>Equivalent to:</p>
	 * <pre>setPassword(password, oldPassword, true);</pre>
	 * @param password The new password
	 * @param oldPassword The old password
	 * @throws IllegalArgumentException when password is empty
	 *         or the old password does not match
	 * @see #setPassword(String, String, boolean)
	 */
	public final void setPassword(String password, String oldPassword) {
		setPassword(password, oldPassword, true);
	}

	/**
	 * Sets the password.
	 * @param password The new password
	 * @param oldPassword The old password (optional if checkOld is false)
	 * @param checkOld Whether to check the old password
	 * @throws IllegalArgumentException when password is empty
	 *         or the old password does not match
	 */
	public void setPassword(String password, String oldPassword,
			boolean checkOld) {
		if (password == null || password.isEmpty())
			throw new IllegalArgumentException(
					"The new password cannot be empty");
		MessageDigestPasswordEncoder enc = getPasswordEncoder();
		Object salt = getSalt();
		if (checkOld && !enc.isPasswordValid(this.password, oldPassword, salt))
			throw new IllegalArgumentException("The old password is not valid");
		this.password = enc.encodePassword(password, salt);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Checks whether the user's account is enabled.
	 * @return Whether the user is enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables the user.
	 * <p>No effect is the user is already enabled.</p>
	 */
	public void enable() {
		this.enabled = true;
	}

	/**
	 * Disables the user.
	 * <p>No effect is the user is already disabled.</p>
	 */
	public void disable() {
		this.enabled = false;
	}

	/**
	 * Checks whether the user is an administrator.
	 * @return true iff the user is an administrator
	 */
	public boolean isAdmin() {
		return roles.contains(Role.ADMIN);
	}

	/**
	 * Sets whether the user is an administrator.
	 * @param isAdmin Whether the user is an administrator
	 */
	public void setAdmin(boolean isAdmin) {
		if (isAdmin) {
			roles.add(Role.ADMIN);
		} else {
			roles.remove(Role.ADMIN);
		}
	}

	/**
	 * Gets the user roles.
	 * @return The user roles
	 */
	public Set<Role> getRoles() {
		return roles;
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
