package com.hermes.owasphotel.web.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.Role;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.HotelForm;

/**
 * Controller for hotels.
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {

	public static final int PAGE_COUNT = 10;
	public static final int TOP_COUNT = 3;

	@Autowired
	private HotelService hotelService;

	@Autowired
	private UserService userService;

	@Autowired
	private HttpServletRequest request;

	public HotelService getHotelService() {
		return hotelService;
	}

	public void setHotelService(HotelService hotelService) {
		this.hotelService = hotelService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Initializes the editors.
	 * @param binder The data binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Property editor for dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());

	}

	private static String redirectTo(Integer id) {
		String r = "redirect:/hotel";
		if (id != null)
			r += "/" + id;
		return r;
	}

	private <E> PagedListHolder<E> setPagedList(Model model, String name,
			List<E> list, String title) {
		if (title != null) {
			model.addAttribute("pageTitle", title);
		}
		return Utils.setPagedList(request, model, name, list, PAGE_COUNT);
	}

	/**
	 * View the list of approved hotels.
	 * @param model The model
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String viewHotels(Model model) {
		List<Hotel> hotels = hotelService.listApproved();
		setPagedList(model, "hotels", hotels, null);
		return "hotel/list";
	}

	/**
	 * View the list of all hotels.
	 * @param model The model
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "all")
	public String viewHotelsAll(Model model) {
		List<Hotel> hotels = hotelService.listAll();
		setPagedList(model, "hotels", hotels, "All hotels");
		return "hotel/list";
	}

	/**
	 * View the list of not approved hotels.
	 * @param model The model
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "toApprove")
	@PreAuthorize("hasRole('ADMIN')")
	public String viewHotelsNotApproved(Model model) {
		List<Hotel> hotels = hotelService.listNotApproved();
		setPagedList(model, "hotels", hotels, "Hotels to approve");
		model.addAttribute("hotelTableType", "hotelTableApprove.jsp");
		return "hotel/list";
	}

	/**
	 * View the list of top hotels.
	 * @param model The model
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "top")
	public String viewTopHotels(Model model) {
		List<Hotel> hotels = hotelService.listTopNoted(TOP_COUNT);
		setPagedList(model, "hotels", hotels, "Top " + TOP_COUNT + " hotels");
		return "hotel/list";
	}

	/**
	 * View the list of managed hotels.
	 * @param model The model
	 * @param auth The authentication
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "managed")
	@PreAuthorize("hasRole('USER')")
	public String viewHotelsManaged(Model model, Authentication auth) {
		List<Hotel> hotels = hotelService.listManagedHotels(auth.getName());
		model.addAttribute("hotels", hotels);
		model.addAttribute("pageTitle", "Managed hotels");
		return "hotel/list";
	}

	/**
	 * View the searched hotels.
	 * <p>If there is a unique hotel matching the search query, a redirect
	 * to that hotel is sent.</p>
	 * @param model The model
	 * @param search The search query
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "search")
	public String viewSearchHotels(Model model, @RequestParam("t") String search) {
		// try to find by name
		try {
			Hotel hotel = hotelService.getByName(search);
			return redirectTo(hotel.getId());
		} catch (NoResultException e) {
			// continue execution
		} catch (NonUniqueResultException e) {
			// continue execution
		}

		// return the result list
		List<Hotel> hotels = hotelService.listSearchQuery(search);
		setPagedList(model, "hotels", hotels, "Search: " + search);
		return "hotel/list";
	}

	/**
	 * Gets the list of hotel names used for autocomplete.
	 * @param query The typed query
	 * @return The list of hotels for autocomplete
	 */
	@RequestMapping(method = RequestMethod.GET, value = "searchAutocomplete")
	@ResponseBody
	public List<String> getHotels(@RequestParam("t") String query) {
		return hotelService.findForAutoComplete(query);
	}

	/**
	 * View of a hotel.
	 * <p>Model: "hotel" is set to the {@link Hotel} instance.</p>
	 * @param model The model
	 * @param id The hotel id
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id) {
		Hotel hotel = hotelService.getById(id);
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}

	/**
	 * Adds a comment.
	 * @param hotelId The hotel id
	 * @param user The user (optional)
	 * @param text The text
	 * @param note The note
	 * @param redirectAttrs Redirect attributes
	 * @return A redirect
	 * @see HotelService#addComment(Integer, String, int, String)
	 */
	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment")
	public String addComment(@PathVariable("id") Integer hotelId,
			Authentication user, @RequestParam String text,
			@RequestParam int note, RedirectAttributes redirectAttrs) {
		hotelService.addComment(hotelId, user == null ? null : user.getName(),
				note, text);
		Utils.successMessage(redirectAttrs, "Comment added.");
		return redirectTo(hotelId);
	}

	/**
	 * Deletes a comment.
	 * @param hotelId The id of the hotel
	 * @param commentId The id of the comment
	 * @param redirectAttrs Redirect attributes
	 * @return A redirect
	 * @see HotelService#deleteComment(Integer)
	 */
	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment", params = "delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteComment(@PathVariable("id") Integer hotelId,
			@RequestParam("delete") Integer commentId,
			RedirectAttributes redirectAttrs) {
		hotelService.deleteComment(commentId);
		Utils.successMessage(redirectAttrs, "Comment deleted.");
		return redirectTo(hotelId);
	}

	/**
	 * View a hotel create form.
	 * @param model The model
	 * @param form The hotel form
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "create")
	@PreAuthorize("hasRole('USER')")
	public String viewCreateHotel(@ModelAttribute("hotel") HotelForm form) {
		return "hotel/update";
	}

	/**
	 * Creates a new hotel.
	 * @param form The form
	 * @param binding The form binding
	 * @param auth The authentication
	 * @param redirectAttrs Redirect attributes
	 * @return A redirect or the form when the update was not successful
	 */
	@RequestMapping(method = RequestMethod.POST, value = "create")
	@PreAuthorize("hasRole('USER')")
	public String createHotel(@Valid @ModelAttribute("hotel") HotelForm form,
			BindingResult binding, Authentication auth,
			RedirectAttributes redirectAttrs) {
		if (binding.hasErrors()) {
			return "hotel/update";
		}
		User user = Utils.getUser(auth, userService);
		if (user == null)
			throw new IllegalStateException("Authentified as an invalid user");
		Hotel hotel = form.makeNew(user);
		hotelService.save(hotel);
		Utils.successMessage(redirectAttrs, "Hotel '" + hotel.getName()
				+ "' created.");
		return redirectTo(hotel.getId());
	}

	private void checkEdit(Hotel hotel, Authentication auth) {
		User user = Utils.getUser(auth, userService);
		if (user == null
				|| !(user.equals(hotel.getManager()) || user.getRoles()
						.contains(Role.ADMIN)))
			throw new AccessDeniedException("Cannot edit that hotel");
	}

	/**
	 * View a hotel update form.
	 * @param model The model
	 * @param auth The authentication
	 * @param hotelId The hotel id
	 * @return The view
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String viewUpdateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId) {
		Hotel hotel = hotelService.getById(hotelId);
		checkEdit(hotel, auth);
		model.addAttribute("hotel", new HotelForm(hotel));
		return "hotel/update";
	}

	/**
	 * Updates a hotel.
	 * @param model The model
	 * @param auth The authentication
	 * @param hotelId The hotel to update
	 * @param form The form
	 * @param result The form binding result
	 * @param redirectAttrs Redirect attributes
	 * @return A redirect or the form when the update was not successful
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String updateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId,
			@Valid @ModelAttribute("hotel") HotelForm form,
			BindingResult result, RedirectAttributes redirectAttrs) {
		Hotel hotel = hotelService.getById(hotelId);
		checkEdit(hotel, auth);
		try {
			if (!result.hasErrors())
				form.update(hotel, userService);
		} catch (IllegalArgumentException e) {
			result.addError(new ObjectError("Hotel", "Invalid parameter: "
					+ e.getMessage()));
		}
		if (result.hasErrors()) {
			form.setId(hotelId);
			return "hotel/update";
		}
		hotel = hotelService.update(hotel);
		Utils.successMessage(redirectAttrs, "Hotel updated.");
		return redirectTo(hotel.getId());
	}

	/**
	 * Approves a hotel.
	 * @param hotelId The hotel id to approve
	 * @param redirectAttrs Redirect attributes
	 * @return A redirect
	 * @see HotelService#approve(Integer)
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public String approveHotel(@PathVariable("id") Integer hotelId,
			RedirectAttributes redirectAttrs) {
		hotelService.approve(hotelId);
		Utils.successMessage(redirectAttrs, "Hotel approved.");
		return redirectTo(hotelId);
	}

	/**
	 * Gets a hotel image.
	 * <p>When the hotel has no associated image, the default one is returned.</p>
	 * @param hotelId The id of the hotel
	 * @return The hotel image
	 * @throws IOException when the image couldn't be read
	 * @see HotelService#getHotelImage(Integer)
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getHotelImage(@PathVariable("id") Integer hotelId)
			throws IOException {
		byte[] img = hotelService.getHotelImage(hotelId);
		if (img == null || img.length == 0) {
			img = getHotelDefaultImage();
		}
		return img;
	}

	/**
	 * Gets the default hotel image.
	 * @return The default hotel image
	 * @throws IOException when the image couldn't be read
	 */
	@RequestMapping(method = RequestMethod.GET, value = "image/default", produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] getHotelDefaultImage() throws IOException {
		ClassPathResource res = new ClassPathResource(
				"/META-INF/img/defaultHotel.png");
		InputStream in = res.getInputStream();
		byte[] img = IOUtils.toByteArray(in);
		IOUtils.closeQuietly(in);
		return img;
	}

}
