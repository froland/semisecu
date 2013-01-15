package com.hermes.owasphotel.web.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.MissingServletRequestParameterException;
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
import com.hermes.owasphotel.service.HotelListItem;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.web.mvc.form.HotelForm;

/**
 * Controller for hotels.
 * 
 * @author k
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
	 * 
	 * @param binder
	 *            The data binder
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

	@RequestMapping(method = RequestMethod.GET)
	public String viewHotels(Model model,
			@RequestParam(defaultValue = "0") int page) {
		List<HotelListItem> hotels = hotelService.listApproved();
		setPagedList(model, "hotels", hotels, null);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "all")
	public String viewHotelsAll(Model model) {
		List<HotelListItem> hotels = hotelService.listAll();
		setPagedList(model, "hotels", hotels, "All hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "toApprove")
	@PreAuthorize("hasRole('ADMIN')")
	public String viewHotelsNotApproved(Model model) {
		List<HotelListItem> hotels = hotelService.listNotApproved();
		setPagedList(model, "hotels", hotels, "Hotels to approve");
		model.addAttribute("hotelTableType", "hotelTableApprove.jsp");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "top")
	public String viewTopHotels(Model model) {
		List<HotelListItem> hotels = hotelService.listTopNoted(TOP_COUNT);
		setPagedList(model, "hotels", hotels, "Top " + TOP_COUNT + " hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "managed")
	@PreAuthorize("hasRole('USER')")
	public String viewHotelsManaged(Model model, Authentication auth) {
		List<HotelListItem> hotels = hotelService.listManagedHotels(auth
				.getName());
		model.addAttribute("hotels", hotels);
		model.addAttribute("pageTitle", "Managed hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "search")
	public String viewSearchHotels(Model model, @RequestParam("t") String search) {
		// try to find by name
		Hotel hotel = hotelService.getByName(search);
		if (hotel != null)
			return redirectTo(hotel.getId());

		// return the result list
		List<HotelListItem> hotels = hotelService.listSearchQuery(search);
		setPagedList(model, "hotels", hotels, "Search: " + search);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "searchAutocomplete")
	@ResponseBody
	public List<String> getHotels(@RequestParam("t") String query) {
		return hotelService.findForAutoComplete(query);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id) {
		Hotel hotel = hotelService.getById(id);
		if (hotel == null)
			throw new IllegalArgumentException("Hotel not found: id=" + id);
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment")
	public String addComment(@PathVariable("id") Integer hotelId,
			Authentication user, @RequestParam String text,
			@RequestParam int note, RedirectAttributes redirectAttrs)
			throws MissingServletRequestParameterException {
		hotelService.addComment(hotelId, user == null ? null : user.getName(),
				note, text);
		Utils.successMessage(redirectAttrs, "Comment added.");
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment", params = "delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String deleteComment(@PathVariable("id") Integer hotelId,
			@RequestParam("delete") Integer comment,
			RedirectAttributes redirectAttrs) {
		hotelService.deleteComment(hotelId, comment);
		Utils.successMessage(redirectAttrs, "Comment deleted.");
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	@PreAuthorize("hasRole('USER')")
	public String viewCreateHotel(@ModelAttribute("hotel") HotelForm dto) {
		return "hotel/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	@PreAuthorize("hasRole('USER')")
	public String createHotel(@Valid @ModelAttribute("hotel") HotelForm dto,
			BindingResult binding, Authentication auth,
			RedirectAttributes redirectAttrs) {
		if (binding.hasErrors()) {
			return "hotel/update";
		}
		User user = getUser(auth);
		Hotel hotel = dto.makeNew(user);
		hotelService.save(hotel);
		Utils.successMessage(redirectAttrs, "Hotel '" + hotel.getName()
				+ "' created.");
		return redirectTo(hotel.getId());
	}

	private static void checkEdit(Hotel hotel, User user) {
		if (hotel == null)
			throw new IllegalArgumentException("Hotel not found");
		if (user == null
				|| !(user.equals(hotel.getManager()) || user.getRoles()
						.contains(Role.ADMIN)))
			throw new AccessDeniedException("Cannot edit that hotel");
	}

	private User getUser(Authentication auth) {
		if (auth == null)
			return null;
		return userService.getByName(auth.getName());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String viewUpdateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId) {
		Hotel hotel = hotelService.getById(hotelId);
		checkEdit(hotel, getUser(auth));
		model.addAttribute("hotel", new HotelForm(hotel));
		return "hotel/update";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String updateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId,
			@Valid @ModelAttribute("hotel") HotelForm dto,
			BindingResult result, RedirectAttributes redirectAttrs) {
		Hotel hotel = hotelService.getById(hotelId);
		checkEdit(hotel, getUser(auth));
		try {
			if (!result.hasErrors())
				dto.update(hotel, userService);
		} catch (IllegalArgumentException e) {
			result.addError(new ObjectError("Hotel", "Invalid parameter: "
					+ e.getMessage()));
		}
		if (result.hasErrors()) {
			dto.setId(hotelId);
			return "hotel/update";
		}
		hotel = hotelService.update(hotel);
		Utils.successMessage(redirectAttrs, "Hotel updated.");
		return redirectTo(hotel.getId());
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/approve")
	@PreAuthorize("hasRole('ADMIN')")
	public String approveHotel(@PathVariable("id") Integer hotelId,
			RedirectAttributes redirectAttrs) {
		hotelService.approve(hotelId);
		Utils.successMessage(redirectAttrs, "Hotel approved.");
		return redirectTo(hotelId);
	}

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
