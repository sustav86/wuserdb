package ua.sustavov.model;

public enum UserRole {

	ROLE_ADMIN("Admin"), ROLE_USER("User");

	private String role;

	UserRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

}
