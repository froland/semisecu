package com.hermes.owasphotel.web.mvc;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.service.HotelDto;
import com.hermes.owasphotel.service.HotelService;
import com.hermes.owasphotel.service.UserService;

/**
 * Controller for hotels.
 * 
 * @author k
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {

	public static final int PAGE_COUNT = 4;
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
		List<Hotel> hotels = hotelService.findApproved();
		setPagedList(model, "hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "all")
	public String viewHotelsAll(Model model) {
		List<Hotel> hotels = hotelService.findAll();
		setPagedList(model, "hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "top")
	public String viewTopHotels(Model model) {
		List<Hotel> hotels = hotelService.findTopNoted(TOP_COUNT);
		model.addAttribute("hotels", hotels);
		model.addAttribute("pageTitle", "Top " + TOP_COUNT + " hotels");
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id) {
		Hotel hotel = hotelService.find(id);
		if (hotel == null)
			throw new ResourceNotFoundException(Hotel.class, id.longValue());
		model.addAttribute("hotel", hotel);
		if (!hotel.isApproved())
			return "hotel/notApproved";
		return "hotel/view";
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment")
	public String addComment(@PathVariable("id") Integer hotelId,
			Principal user, @RequestParam String text,
			@RequestParam(required = false) String name, @RequestParam int note) {
		if (hotelId != null && (name != null || user != null) && text != null) {
			if (name == null)
				name = user.getName();
			hotelService.addComment(hotelId, name, user != null, note, text);
		}
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment", params = "delete")
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
	public String createHotel(@Valid @ModelAttribute("hotel") HotelDto dto,
			BindingResult binding, Principal p) {
		if (binding.hasErrors()) {
			return "hotel/update";
		}
		User user = userService.find(p.getName());
		Hotel hotel = dto.makeNew(user);
		hotelService.save(hotel);
		return redirectTo(hotel.getId());
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/update")
	public String viewUpdateHotel(Model model,
			@PathVariable("id") Integer hotelId) {
		Hotel hotel = hotelService.find(hotelId);
		model.addAttribute("hotel", hotel);
		return "hotel/update";
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT }, value = "{id}/update")
	// TODO @PreAuthorize("hasPermission(#hotel, 'user')")
	public String updateHotel(Model model, @PathVariable("id") Integer hotelId,
			@Valid @ModelAttribute("hotel") HotelDto dto, BindingResult result) {
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
		hotelService.approve(hotelService.find(hotelId));
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}/image", produces = "image/png")
	@ResponseBody
	public byte[] getHotelImage(@PathVariable("id") Integer hotelId) {
		return hotelService.getHotelImage(hotelId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/image")
	public String uploadHotelImage(@PathVariable("id") Integer hotelId,
			UploadedImage upload) {
		CommonsMultipartFile file = upload.getFile();
		if (file != null) {
			hotelService.setHotelImage(hotelId, file.getBytes());
		}
		return "redirect:/hotel/" + hotelId + "/update";
	}
}
