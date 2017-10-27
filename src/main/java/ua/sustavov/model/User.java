package ua.sustavov.model;

import java.io.Serializable;

public class User extends AbstractNamedEntity implements Serializable {

	private static final long serialVersionUID = -7724896415079617527L;

	private String surname;
	private String email;
	private boolean enabled;
	private String password;
	private UserRole role;

	public User() {

	}

	public User(Integer id, String name, String surname, String email, String password, UserRole role) {
		super(id, name);
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.setEnabled(true);
		this.role = role;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + getId() + ", email=" + email + ", name=" + name + ", surname=" + surname + ", role="
				+ role + '}';
	}

}
