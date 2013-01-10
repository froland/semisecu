package com.hermes.owasphotel.web.mvc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hermes.owasphotel.domain.Role;

final class Utils {
	private Utils() {
	}

	public static boolean hasRole(Authentication auth, Role role) {
		if (auth == null)
			return false;
		return hasRole(auth.getAuthorities(), role);
	}

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
