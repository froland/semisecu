package com.hermes.owasphotel.web.security;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.NoResultException;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;

/**
 * A user authentication token that uses directly {@link User}.
 * @see #getPrincipal()
 */
public class UserAuthentication extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private User user;

	/**
	 * Initializes this authentication.
	 * @param user The user object
	 * @param authorities The granted authorities
	 * @throws NullPointerException when user is null
	 */
	public UserAuthentication(User user,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		if (user == null)
			throw new NullPointerException("user is null");
		this.user = user;
	}

	/**
	 * Initializes this authentication.
	 * @param user The user object
	 * @throws NullPointerException when user is null
	 */
	public UserAuthentication(User user) {
		this(user, getAuthorities(user));
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(
			User user) {
		HashSet<GrantedAuthority> auth = new HashSet<GrantedAuthority>();
		for (Role role : user.getRoles()) {
			auth.add(new SimpleGrantedAuthority(role.toString()));
		}
		return auth;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public User getPrincipal() {
		return user;
	}

	public Integer getId() {
		return user.getId();
	}

	@Override
	public String getName() {
		return user.getName();
	}

	/**
	 * Refreshes the principal object using a user service.
	 * @param userService The user service
	 * @throws NullPointerException when userService is null
	 * @throws BadCredentialsException when the user couldn't be found
	 */
	public void refresh(UserService userService) {
		User user;
		try {
			user = userService.getById(this.user.getId());
		} catch (NoResultException e) {
			throw new BadCredentialsException("User account not found", e);
		}
		this.user = user;
	}

}
