package ua.sustavov;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import ua.sustavov.login.LoginPage;
import ua.sustavov.login.RegisterPage;
import ua.sustavov.view.UsersPage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see ua.sustavov.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		getMarkupSettings().setStripWicketTags(true);

		mountPage("/login", LoginPage.class);
		mountPage("/register", RegisterPage.class);
		mountPage("/users", UsersPage.class);

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

	}

	@Override
	public Session newSession(Request request, Response response) {
		return new CurrentSession(request);
	}

}
