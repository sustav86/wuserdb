package ua.sustavov.login;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ua.sustavov.model.User;
import ua.sustavov.model.UserRole;
import ua.sustavov.service.UserService;
import ua.sustavov.view.validators.NamePolicyValidator;
import ua.sustavov.view.validators.PasswordPolicyValidator;

public class RegisterPage extends WebPage {

	private static final long serialVersionUID = -6255795860256232637L;

	@SpringBean
	private UserService userService;

	private Form<?> form;

	public UserService getUserService() {
		return userService;
	}

	public RegisterPage(final PageParameters parameters) {
		super(parameters);

		User user = new User();
		List<UserRole> list = Arrays.asList(UserRole.values());

		add(new FeedbackPanel("succes", new ExactErrorLevelFilter(FeedbackMessage.SUCCESS)));
		add(new FeedbackPanel("feedback", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));

		FormComponent<String> name = new TextField<>("name", new PropertyModel<String>(user, "name"));
		name.setRequired(true);
		name.add(new NamePolicyValidator());

		FormComponent<String> surname = new TextField<>("surname", new PropertyModel<String>(user, "surname"));

		FormComponent<String> email = new TextField<>("email", new PropertyModel<String>(user, "email"));
		email.setRequired(true);
		email.add(EmailAddressValidator.getInstance());

		FormComponent<?> password = new PasswordTextField("password", new PropertyModel<String>(user, "password"));

		FormComponent<?> password2 = new PasswordTextField("password2", Model.of(""));

		DropDownChoice<UserRole> userRole = new DropDownChoice<UserRole>("role",
				new PropertyModel<UserRole>(user, "role"), list);
		userRole.setRequired(true);

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

		Button button = new Button("singup") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				User checkUser = getUserService().getByName(user.getName());
				if (checkUser != null && user.getEmail().equals(checkUser.getEmail())) {
					name.error((IValidationError) new ValidationError().addKey("userFound"));
				} else {
					getUserService().create(user);
					success("Congratulation! You are register, please, Log In");
					setResponsePage(LoginPage.class);
				}

			}
		};

		form.add(new PasswordPolicyValidator(password, password2));

		add(form);
		form.add(name);
		form.add(surname);
		form.add(email);
		form.add(password);
		form.add(password2);
		form.add(userRole);
		form.add(button);
	}

}
