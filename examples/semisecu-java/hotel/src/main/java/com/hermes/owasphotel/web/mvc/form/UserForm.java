package com.hermes.owasphotel.web.mvc.form;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;

import com.hermes.owasphotel.domain.User;
import com.hermes.owasphotel.validation.SameValue;

@SameValue(fieldNames = { "password", "retypedPassword" }, message = "Both passwords are not equal")
public class UserForm {

	private Integer id;

	@NotEmpty(message = "Your name may not be blank")
	private String name;

	private String oldPassword;
	private String password;
	private String retypedPassword;

	@Email(message = "You must enter a valid e-mail")
	private String email;

	public UserForm() {
		// default constructor
	}

	public UserForm(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
	}

	public void update(User user) {
		user.setName(name);
		user.setEmail(email);
	}

	public void updatePassword(User domain, boolean asAdmin) {
		String field = asAdmin ? password : oldPassword;
		if (field != null && !field.isEmpty()) {
			domain.setPassword(password, oldPassword, !asAdmin);
		}
	}

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
