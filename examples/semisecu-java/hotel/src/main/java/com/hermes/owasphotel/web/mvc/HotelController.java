package com.hermes.owasphotel.web.mvc;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.hermes.owasphotel.domain.Hotel;
import com.hermes.owasphotel.service.HotelDto;
import com.hermes.owasphotel.service.HotelService;

/**
 * Controller for hotels.
 * 
 * @author k
 */
@Controller
@RequestMapping("/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	/**
	 * Initializes the editors.
	 * 
	 * @param binder
	 *            The data binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// property editor for dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	private static String redirectTo(Integer id) {
		String r = "redirect:/hotel";
		if (id != null)
			r += "/" + id;
		return r;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewHotels(Model model) {
		List<Hotel> hotels = hotelService.findApproved();
		model.addAttribute("hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "all")
	public String viewHotelsAll(Model model) {
		List<Hotel> hotels = hotelService.findAll();
		model.addAttribute("hotels", hotels);
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
			@RequestParam String text, Principal user) {
		if (hotelId != null && user != null && text != null) {
			hotelService.addComment(hotelId, user.getName(), text);
		}
		return redirectTo(hotelId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "create")
	public String viewCreateHotel(@ModelAttribute("hotel") HotelDto dto) {
		return "hotel/update";
	}

	@RequestMapping(method = RequestMethod.POST, value = "create")
	public String createHotel(@Valid @ModelAttribute("hotel") HotelDto dto,
			BindingResult binding) {
		if (binding.hasErrors()) {
			return "hotel/update";
		}
		Hotel hotel = dto.makeNew();
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
	public String approveHotel(@PathVariable("id") Integer hotelId) {
		hotelService.approve(hotelService.find(hotelId));
		return redirectTo(hotelId);
	}
}
