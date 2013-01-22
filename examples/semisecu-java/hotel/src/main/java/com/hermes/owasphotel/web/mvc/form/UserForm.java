package com.hermes.owasphotel.web.mvc.form;

import org.apache.bval.constraints.Email;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.validation.NotBlank;
import com.hermes.owasphotel.validation.SameValue;

/**
 * Form used to update user accounts.
 */
@SameValue(fieldNames = { "password", "retypedPassword" }, message = "Both passwords are not equal")
public class UserForm {

	private Integer id;

	@NotBlank(message = "Your name may not be blank")
	private String name;

	private String oldPassword;
	private String password;
	private String retypedPassword;

	@Email(message = "You must enter a valid e-mail")
	private String email;

	public UserForm() {
		// default constructor
	}

	/**
	 * Initializes this form.
	 * @param user The user
	 */
	public UserForm(User user) {
		if (user == null)
			throw new IllegalArgumentException("user is null");
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
	}

	/**
	 * Updates user attributes.
	 * @param user The user to update
	 */
	public void update(User user) {
		if (user == null)
			throw new IllegalArgumentException("user is null");
		user.setName(name);
		user.setEmail(email);
	}

	/**
	 * Updates the user's password.
	 * <p>When updating the password, the old password is checked only when
	 * updating as a normal user.
	 * This method has no effect when the user gives no old password or when
	 * the admin does not set a new password.</p>
	 * @param user The user to update
	 * @param asAdming Indicator whether the update is done as admin
	 * @throws IllegalArgumentException when the password couldn't be updated
	 * @see User#setPassword(String, String, boolean)
	 */
	public void updatePassword(User user, boolean asAdmin) {
		if (user == null)
			throw new IllegalArgumentException("user is null");
		String field = asAdmin ? password : oldPassword;
		if (field != null && !field.isEmpty()) {
			user.setPassword(password, oldPassword, !asAdmin);
		}
	}

	/**
	 * Creates a new user.
	 * @return A new user
	 * @see User#User(String, String)
	 */
	public User makeNew() {
		User u = new User(name, password);
		update(u);
		return u;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRetypedPassword() {
		return retypedPassword;
	}

	public void setRetypedPassword(String retypedPassword) {
		this.retypedPassword = retypedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
