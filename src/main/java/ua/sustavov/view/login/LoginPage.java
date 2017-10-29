package ua.sustavov.view.login;

import java.util.Locale;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;

import ua.sustavov.CurrentSession;
import ua.sustavov.LoggedUser;
import ua.sustavov.model.User;
import ua.sustavov.service.UserService;
import ua.sustavov.view.UsersPage;
import ua.sustavov.view.register.RegisterPage;
import ua.sustavov.view.validators.ExactErrorLevelFilter;
import ua.sustavov.view.validators.NamePolicyValidator;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 204217978937873329L;

	@SpringBean
	private UserService userService;

	private String name;
	private String password;

	@SuppressWarnings("LoginForm")
	class LoginForm extends Form<Void> {

		private static final long serialVersionUID = 4237597734918239015L;

		public LoginForm(String id) {
			super(id);
		}

		@Override
		protected void onSubmit() {
			String userName = getName();
			String userPassword = getPassword();
			if (authentication(userName, userPassword)) {
				setResponsePage(UsersPage.class);
			} else {
				error((IValidationError) new ValidationError().addKey("userNotFound"));
			}

		}

	}

	public LoginPage(final PageParameters parameters) {
		super(parameters);

		CurrentSession.get().setLocale(new Locale( "en", "EN" ));
		
		TextField<String> nameTextField = new TextField<String>("name", new PropertyModel<String>(this, "name"));
		nameTextField.setRequired(true);
		nameTextField.add(new NamePolicyValidator());
		
		PasswordTextField passwordTextField = new PasswordTextField("password",
				new PropertyModel<String>(this, "password"));

		FeedbackPanel errorFeedBackPanel = new FeedbackPanel("feedback",
				new ExactErrorLevelFilter(FeedbackMessage.ERROR));
		FeedbackPanel succesFeedBackPanel = new FeedbackPanel("succes",
				new ExactErrorLevelFilter(FeedbackMessage.SUCCESS));

		Link<Void> singUpButton = new Link<Void>("singup") {

			private static final long serialVersionUID = 1384569312186511804L;

			@Override
			public void onClick() {
				PageParameters pageParameters = new PageParameters();
				pageParameters.add("name", getName() == null ? "" : getName());
				RegisterPage registerPage = new RegisterPage(pageParameters);
				setResponsePage(registerPage);
			}

		};

		Form<Void> loginForm = new LoginForm("loginForm");
		loginForm.add(nameTextField);
		loginForm.add(passwordTextField);
		add(errorFeedBackPanel);
		add(succesFeedBackPanel);
		add(loginForm);
		add(singUpButton);

	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	@SuppressWarnings("unused")
	private boolean authentication(final String name, final String password) {
		User checkUser = userService.getByName(getName());
		if (checkUser != null) {
			setLogginUserSession(checkUser);
			return true;
		}

		return false;
	}

	private void setLogginUserSession(final User checkUser) {
		CurrentSession.get().setLoggedUser(new LoggedUser(checkUser.getId(), checkUser.getName(), checkUser.getRole()));
	}

}
