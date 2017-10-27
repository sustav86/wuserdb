package ua.sustavov.login;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;

import ua.sustavov.CurrentSession;
import ua.sustavov.LoggedUser;
import ua.sustavov.model.User;
import ua.sustavov.service.UserService;
import ua.sustavov.view.UsersPage;
import ua.sustavov.view.validators.NamePolicyValidator;

public class LoginPage extends WebPage {

	private static final long serialVersionUID = 204217978937873329L;

	@SpringBean
	private UserService userService;

	private Form<?> form;

	public UserService getUserService() {
		return userService;
	}

	public LoginPage(final PageParameters parameters) {
		super(parameters);

		add(new FeedbackPanel("succes", new ExactErrorLevelFilter(FeedbackMessage.SUCCESS)));
		add(new FeedbackPanel("feedback", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));

		FormComponent<String> name = new TextField<String>("name", Model.of(""));
		name.setLabel(Model.of("Name"));
		name.setRequired(true);
		name.add(new NamePolicyValidator());

		FormComponent<?> password = new PasswordTextField("password", Model.of(""));
		password.setLabel(Model.of("Password"));
		password.setRequired(true);

		form = new Form("form") {

			@Override
			protected void onSubmit() {
				super.onSubmit();

			}

			@Override
			protected void onValidate() {
				super.onValidate();

			}

		};

		Button button = new Button("login") {

			@Override
			public void onSubmit() {
				super.onSubmit();
				User checkUser = getUserService().getByName(name.getValue());
				if (checkUser == null || !password.getValue().equals(checkUser.getPassword())) {
					error((IValidationError) new ValidationError().addKey("userNotFound"));
				} else {
					CurrentSession.get()
							.setLoggedUser(new LoggedUser(checkUser.getId(), checkUser.getName(), checkUser.getRole()));
					setResponsePage(UsersPage.class);
				}
			}

		};

		add(form);
		form.add(name);
		form.add(password);
		form.add(button);

		add(new Link<Void>("singup") {

			@Override
			public void onClick() {
				setResponsePage(RegisterPage.class);
			}

		});
	}

}
