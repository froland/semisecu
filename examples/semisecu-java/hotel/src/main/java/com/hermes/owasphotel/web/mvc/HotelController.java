package com.hermes.owasphotel.web.mvc;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hermes.owasphotel.domain.Hotel;
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

	@RequestMapping(method = RequestMethod.GET)
	public String viewHotels(Model model) {
		List<Hotel> hotels = hotelService.findAll();
		model.addAttribute("hotels", hotels);
		return "hotel/list";
	}

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotelComments(Model model, @PathVariable Long id) {
		Hotel hotel = hotelService.find(id);
		if (hotel == null)
			throw new ResourceNotFoundException(Hotel.class, id);
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}

	@RequestMapping(method = RequestMethod.POST, value = "{id}/comment")
	public String addComment(@PathVariable("id") Long hotelId,
			@RequestParam String text, Principal user) {
		if (hotelId != null && user != null && text != null) {
			hotelService.addComment(hotelId, user.getName(), text);
		}
		return "redirect:/hotel/" + hotelId;
	}
}
