package ua.sustavov;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public final class CurrentSession extends WebSession {

	private static final long serialVersionUID = -4144491335569273321L;

	private LoggedUser loggedUser;

	public CurrentSession(Request request) {
		super(request);
	}

	public LoggedUser getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(LoggedUser loggedUser) {
		this.loggedUser = loggedUser;
	}

	public boolean isAuthenticated() {
		return loggedUser != null;
	}
	
	public static CurrentSession get() {
		return (CurrentSession) Session.get();
	}

}
