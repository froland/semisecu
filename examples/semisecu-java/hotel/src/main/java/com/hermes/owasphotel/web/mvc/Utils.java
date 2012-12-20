package com.hermes.owasphotel.web.mvc;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

class Utils {
	private Utils() {
	}

	public static boolean hasRole(Authentication auth, String role) {
		if (auth == null)
			return false;
		return hasRole(auth.getAuthorities(), role);
	}

	public static boolean hasRole(Iterable<? extends GrantedAuthority> auth,
			String role) {
		if (auth == null || role == null)
			return false;
		for (GrantedAuthority a : auth) {
			if (role.equals(a.getAuthority()))
				return true;
		}
		return false;
	}
}
