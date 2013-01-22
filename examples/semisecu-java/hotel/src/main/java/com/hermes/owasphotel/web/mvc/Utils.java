package com.hermes.owasphotel.web.mvc;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.security.UserAuthentication;

/**
 * Utilities for controllers.
 */
final class Utils {
	private Utils() {
	}

	/**
	 * Gets a {@link User} from an {@link Authentication}.
	 * @param auth The authentication
	 * @param service The user service
	 * @return The user or <code>null</code> when not authenticated
	 */
	public static User getUser(Authentication auth, UserService service) {
		if (auth == null)
			return null;
		if (auth instanceof UserAuthentication) {
			return ((UserAuthentication) auth).getPrincipal();
		} else {
			try {
				return service.getByName(auth.getName());
			} catch (NoResultException e) {
				return null;
			}
		}
	}

	/**
	 * Checks whether the authentication has a given role.
	 * @param auth The authentication
	 * @param role The role
	 * @return true iff the authentication object has the role
	 */
	public static boolean hasRole(Authentication auth, Role role) {
		if (auth == null)
			return false;
		return hasRole(auth.getAuthorities(), role);
	}

	/**
	 * Checks whether the authentication has a given role.
	 * @param auth The granted authorities
	 * @param role The role
	 * @return true iff an authority of the same name as the role exists
	 */
	public static boolean hasRole(Iterable<? extends GrantedAuthority> auth,
			Role role) {
		if (auth == null || role == null)
			return false;
		for (GrantedAuthority a : auth) {
			if (role.toString().equals(a.getAuthority()))
				return true;
		}
		return false;
	}

	/**
	 * Sets a paged list.
	 * <p>The number of the current page is a parameter read from the
	 * request named "page".</p>
	 * <p>Sets the following elements in the model:</p>
	 * <ul>
	 * <li>name: the page (sublist of the given list)</li>
	 * <li>"pagedListHolder": the returned value
	 * </ul>
	 * @param request The HTTP request
	 * @param model The model
	 * @param name The name of the list to set
	 * @param list The list
	 * @param pageSize The size of a page
	 * @return The paged holder
	 */
	public static <E> PagedListHolder<E> setPagedList(
			HttpServletRequest request, Model model, String name, List<E> list,
			int pageSize) {
		PagedListHolder<E> paged = new PagedListHolder<E>(list);
		paged.setPageSize(pageSize);
		String page = request.getParameter("page");
		if (page != null) {
			try {
				paged.setPage(Integer.parseInt(page));
			} catch (IllegalArgumentException e) {
				// ignore parsing error
			}
		}
		model.addAttribute(name, paged.getPageList());
		model.addAttribute("pagedListHolder", paged);
		return paged;
	}

	/**
	 * Adds a SUCCESS_MESSAGE to the redirect attributes as a flash message.
	 * @param redirectAttrs Redirect attributes
	 * @param message The message
	 */
	public static void successMessage(RedirectAttributes redirectAttrs,
			String message) {
		final String MSG_KEY = "SUCCESS_MESSAGE";
		Object oldMsg = redirectAttrs.getFlashAttributes().get(MSG_KEY);
		if (oldMsg != null) {
			message = oldMsg.toString() + "\n" + message;
		}
		redirectAttrs.addFlashAttribute(MSG_KEY, message);
	}
}
