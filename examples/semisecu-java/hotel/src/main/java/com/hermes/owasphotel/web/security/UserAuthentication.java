package com.hermes.owasphotel.web.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;

public class UserAuthentication extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private User user;

	public UserAuthentication(User user,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		if (user == null)
			throw new NullPointerException("user is null");
		this.user = user;
	}

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

	public void refresh(UserService userService) {
		User user = userService.getById(this.user.getId());
		if (user == null) {
			throw new BadCredentialsException("User account not found");
		}
		this.user = user;
	}

}
