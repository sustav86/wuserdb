package ua.sustavov.view.register;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ua.sustavov.model.User;
import ua.sustavov.model.UserRole;
import ua.sustavov.service.UserService;
import ua.sustavov.view.login.LoginPage;
import ua.sustavov.view.validators.ExactErrorLevelFilter;
import ua.sustavov.view.validators.NamePolicyValidator;
import ua.sustavov.view.validators.PasswordPolicyValidator;

public class RegisterPage extends WebPage {

	private static final long serialVersionUID = -6255795860256232637L;

	@SpringBean
	private UserService userService;

	public RegisterPage(final PageParameters parameters) {
		super(parameters);

		User user = new User();
		List<UserRole> list = Arrays.asList(UserRole.values());

		Form<User> registerForm = new RegisterForm("registerForm", new CompoundPropertyModel<User>(user));

		TextField<String> nameTextField = new TextField<>("name");
		nameTextField.setRequired(true);
		nameTextField.add(new NamePolicyValidator());

		TextField<String> surnameTextField = new TextField<>("surname");

		TextField<String> emailTextField = new TextField<>("email");
		emailTextField.setRequired(true);
		emailTextField.add(EmailAddressValidator.getInstance());

		PasswordTextField passwordTextField = new PasswordTextField("password");

		PasswordTextField passwordTextFieldValid = new PasswordTextField("password2", Model.of(""));

		DropDownChoice<UserRole> userRoleChoice = new DropDownChoice<UserRole>("role", list);
		userRoleChoice.setRequired(true);

		FeedbackPanel errorFeedBackPanel = new FeedbackPanel("feedback",
				new ExactErrorLevelFilter(FeedbackMessage.ERROR));
		FeedbackPanel succesFeedBackPanel = new FeedbackPanel("succes",
				new ExactErrorLevelFilter(FeedbackMessage.SUCCESS));

		registerForm.add(new PasswordPolicyValidator(passwordTextField, passwordTextFieldValid));
		registerForm.add(nameTextField);
		registerForm.add(surnameTextField);
		registerForm.add(emailTextField);
		registerForm.add(passwordTextField);
		registerForm.add(passwordTextFieldValid);
		registerForm.add(userRoleChoice);
		add(errorFeedBackPanel);
		add(succesFeedBackPanel);
		add(registerForm);

	}

	@SuppressWarnings("RegisterForm")
	class RegisterForm extends Form<User> {

		private static final long serialVersionUID = 8014720086340012056L;

		public RegisterForm(String id, IModel<User> model) {
			super(id, model);

		}

		@Override
		protected void onSubmit() {
			User registrationUser = getModelObject();
			if (findRegisteredUser(registrationUser)) {
				userService.create(registrationUser);
				success("Congratulation! You are register, please, Log In");
				setResponsePage(LoginPage.class);
			}

		}

	}

	@SuppressWarnings("unused")
	private boolean findRegisteredUser(final User registrationUser) {
		User checkUser = userService.getByEmail(registrationUser.getEmail());
		if (checkUser != null) {
			error((IValidationError) new ValidationError().addKey("userFound"));
			return false;
		}

		return true;
	}

}
