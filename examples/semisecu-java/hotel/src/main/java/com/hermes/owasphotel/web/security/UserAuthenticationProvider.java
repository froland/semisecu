package com.hermes.owasphotel.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;

/**
 * Authentication provider for {@link UserAuthentication}.
 */
public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UserAuthentication auth = null;

		if (authentication instanceof UserAuthentication) {
			auth = (UserAuthentication) authentication;
			auth.refresh(userService);
		} else {
			String username = String.valueOf(authentication.getName());
			String password = String.valueOf(authentication.getCredentials());

			User user = userService.getByName(username);
			if (user == null || !user.checkPassword(password))
				throw new BadCredentialsException("Bad credentials");

			auth = new UserAuthentication(user);
		}

		User user = auth.getPrincipal();
		auth.setAuthenticated(user.isEnabled());

		if (!user.isEnabled()) {
			throw new DisabledException("User is disabled");
		}

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UserAuthentication.class.isAssignableFrom(authentication)
				|| UsernamePasswordAuthenticationToken.class
						.isAssignableFrom(authentication);
	}

}
