package com.hermes.owasphotel.service;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

import com.hermes.owasphotel.domain.User;

@ScriptAssert(lang = "jexl", script = "_.password == _.retypedPassword", alias = "_", message = "Both passwords are not equal")
public class UserDto extends GenericDto<Integer, User> {

	@NotBlank(message = "Your name may not be blank")
	private String name;

	@Length(min = 5, message = "Your password must have at least 5 character")
	private String password;

	private String retypedPassword;

	@Email(message = "You must enter a valid e-mail")
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public void read(User u) {
		this.setId(u.getId());
		this.email = u.getEmail();
		this.name = u.getName();
	}

	public User makeNew() {
		User u = new User(name);
		update(u);
		return u;
	}

}
