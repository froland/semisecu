Java: Insecure Direct Object References
=======================================

Scenario summary
----------------

When users create hotels, these are not approved and do not show in the
results of a search. However, these can still be accessed by directly typing
their URL.

Scenario description
--------------------

1. Create some hotel
2. When adding the hotel you are redirected on the hotel page
2. Copy the URL of the hotel. The last number in the address is the hotel ID
3. Log out
4. Paste the copied URL or place the copied id in any valid address
using an hotel id.

Vulnerable code
---------------

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id) {
		Hotel hotel = hotelService.getById(id);
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}

Whether a hotel has been approved is never checked when gaining access.

Preventing the attack
---------------------

The permissions have to be checked before working on data.
This can be done using Spring annotation *@PreAuthorize* for simple
cases (e.g. `ÙserController.viewUser(..)`). 

In this case, which is quite complex (you want to allow access to
approved hotel and let hotel owner and admin see it for modification
or validation) you may do something like that:

	@RequestMapping(method = RequestMethod.GET, value = "{id}")
	public String viewHotel(Model model, @PathVariable Integer id,
			Authentication auth) {
		Hotel hotel = hotelService.getById(id);		
		//If the hotel isn't approved you are just allowed to access it to edit it.
		if (!hotel.isApproved())
			checkEdit(hotel, auth);
		model.addAttribute("hotel", hotel);
		return "hotel/view";
	}
	
	private void checkEdit(Hotel hotel, Authentication auth) {
		User user = Utils.getUser(auth, userService);
		if (user == null
				|| !(user.equals(hotel.getManager()) || user.getRoles()
						.contains(Role.ADMIN)))
			throw new AccessDeniedException("Cannot access
		that hotel page");
	}
	
An other way to solve this problem is to avoid giving direct
references to object to the browser. You just give it some indirect
references and keep the conversion table for yourself.
