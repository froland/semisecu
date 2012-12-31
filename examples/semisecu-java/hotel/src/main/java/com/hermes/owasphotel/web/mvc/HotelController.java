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

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;
import com.hermes.owasphotel.service.dto.HotelDto;
import com.hermes.owasphotel.service.dto.HotelListItemDto;

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
			List<E> list) {
		PagedListHolder<E> paged = new PagedListHolder<E>(list);
		paged.setPageSize(PAGE_COUNT);
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

	@RequestMapping(method = RequestMethod.GET)
	public String viewHotels(Model model,
			@RequestParam(defaultValue = "0") int page) {
		List<HotelListItemDto> hotels = hotelService.listApproved();
		setPagedList(model, "hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "all")
	public String viewHotelsAll(Model model) {
		List<HotelListItemDto> hotels = hotelService.listAll();
		setPagedList(model, "hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "toApprove")
	@PreAuthorize("hasRole('admin')")
	public String viewHotelsNotApproved(Model model) {
		List<HotelListItemDto> hotels = hotelService.listNotApproved();
		setPagedList(model, "hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "top")
	public String viewTopHotels(Model model) {
		List<HotelListItemDto> hotels = hotelService.listTopNoted(TOP_COUNT);
		model.addAttribute("hotels", hotels);
		model.addAttribute("pageTitle", "Top " + TOP_COUNT + " hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "managed")
	@PreAuthorize("hasRole('user')")
	public String viewHotelsManaged(Model model, Authentication auth) {
		List<HotelListItemDto> hotels = hotelService.listManagedHotels(auth
				.getName());
		model.addAttribute("hotels", hotels);
		model.addAttribute("pageTitle", "Managed hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "search")
	public String viewSearchHotels(Model model, @RequestParam("t") String search) {
		// try to find by name
		Hotel hotel = hotelService.findByName(search);
		if (hotel != null)
			return redirectTo(hotel.getId());

		// return the result list
		List<HotelListItemDto> hotels = hotelService.listSearchQuery(search);
		setPagedList(model, "hotels", hotels);
		model.addAttribute("pageTitle", "Search: " + search);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "searchAutocomplete")
	@ResponseBody
	public List<String> getHotels(@RequestParam("t") String query) {
		return hotelService.findForAutoComplete(query);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id) {
		Hotel hotel = hotelService.find(id);
		if (hotel == null)
			throw new ResourceNotFoundException(Hotel.class, id.longValue());
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment")
	public String addComment(@PathVariable("id") Integer hotelId,
			Authentication user, @RequestParam String text,
			@RequestParam(required = false) String name, @RequestParam int note)
			throws MissingServletRequestParameterException {
		if (hotelId != null && (name != null || user != null) && text != null) {
			if (name == null)
				name = user.getName();
			hotelService.addComment(hotelId, name, user != null, note, text);
		} else {
			throw new MissingServletRequestParameterException("name", "String");
		}
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment", params = "delete")
	@PreAuthorize("hasRole('admin')")
	public String deleteComment(@PathVariable("id") Integer hotelId,
			@RequestParam("delete") int comment) {
		hotelService.deleteComment(hotelId, comment);
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	@PreAuthorize("hasRole('user')")
	public String viewCreateHotel(@ModelAttribute("hotel") HotelDto dto) {
		return "hotel/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	@PreAuthorize("hasRole('user')")
	public String createHotel(@Valid @ModelAttribute("hotel") HotelDto dto,
			BindingResult binding, Authentication auth) {
		if (binding.hasErrors()) {
			return "hotel/update";
		}
		User user = getUser(auth);
		Hotel hotel = dto.makeNew(user);
		hotelService.save(hotel);
		return redirectTo(hotel.getId());
	}

	private static void checkEdit(Hotel hotel, User user) {
		if (user == null
				|| hotel == null
				|| !(user.equalsId(hotel.getManager()) || user.getRoles()
						.contains("admin")))
			throw new AccessDeniedException("Cannot edit that hotel");
	}

	private User getUser(Authentication auth) {
		if (auth == null)
			return null;
		return userService.find(auth.getName());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String viewUpdateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId) {
		Hotel hotel = hotelService.find(hotelId);
		checkEdit(hotel, getUser(auth));
		HotelDto dto = new HotelDto();
		dto.read(hotel);
		model.addAttribute("hotel", dto);
		return "hotel/update";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/update")
	@PreAuthorize("isAuthenticated()")
	public String updateHotel(Model model, Authentication auth,
			@PathVariable("id") Integer hotelId,
			@Valid @ModelAttribute("hotel") HotelDto dto, BindingResult result) {
		checkEdit(hotelService.find(hotelId), getUser(auth));
		if (result.hasErrors()) {
			dto.setId(hotelId);
			return "hotel/update";
		}
		Hotel hotel = hotelService.update(hotelId, dto);
		return redirectTo(hotel.getId());
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/approve")
	@PreAuthorize("hasRole('admin')")
	public String approveHotel(@PathVariable("id") Integer hotelId) {
		hotelService.approve(hotelId);
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
