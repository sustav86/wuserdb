package ua.sustavov;

import ua.sustavov.model.AbstractNamedEntity;
import ua.sustavov.model.UserRole;

public final class LoggedUser extends AbstractNamedEntity {

	private static final long serialVersionUID = -5950840361577030985L;
	protected UserRole role;

	public LoggedUser(int id, String name, UserRole role) {
		super(id, name);
		this.role = role;
	}

	public LoggedUser() {

	}

	public UserRole getRole() {
		return role;
	}

	public boolean isAdmin() {
		return role == UserRole.ROLE_ADMIN;
	}

}
