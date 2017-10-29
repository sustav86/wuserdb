package ua.sustavov.view;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ua.sustavov.model.User;
import ua.sustavov.model.UserRole;
import ua.sustavov.view.validators.ExactErrorLevelFilter;
import ua.sustavov.view.validators.NamePolicyValidator;

public class EditPanel extends Panel {

	private static final long serialVersionUID = -4298873343828324226L;

	class PanelForm extends Form<User> {

		private static final long serialVersionUID = -2194025881289182529L;

		public PanelForm(String id, IModel<User> model) {
			super(id, model);
		}

		@Override
		protected void onSubmit() {
			User editUser = getModelObject();
			System.out.println(editUser);
		}

	}

	public EditPanel(String id, IModel<User> model) {
		super(id, model);

		List<UserRole> list = Arrays.asList(UserRole.values());

		Form<User> panelForm = new PanelForm("panelForm", new CompoundPropertyModel<User>(model));

		TextField<String> uidTextField = new TextField<String>("id");
		uidTextField.setEnabled(false);

		TextField<String> nameTextField = new TextField<String>("name");
		nameTextField.setRequired(true);
		nameTextField.add(new NamePolicyValidator());

		TextField<String> surnameTextField = new TextField<>("surname");

		PasswordTextField passwordTextField = new PasswordTextField("password");

		PasswordTextField passwordTextFieldValid = new PasswordTextField("password2", Model.of(""));

		TextField<String> emailTextField = new TextField<>("email");
		emailTextField.setRequired(true);
		emailTextField.add(EmailAddressValidator.getInstance());

		DropDownChoice<UserRole> userRoleChoice = new DropDownChoice<UserRole>("role", list);
		userRoleChoice.setRequired(true);

		FeedbackPanel errorFeedBackPanel = new FeedbackPanel("feedback",
				new ExactErrorLevelFilter(FeedbackMessage.ERROR));
		FeedbackPanel succesFeedBackPanel = new FeedbackPanel("succes",
				new ExactErrorLevelFilter(FeedbackMessage.SUCCESS));

		panelForm.add(uidTextField);
		panelForm.add(nameTextField);
		panelForm.add(surnameTextField);
		panelForm.add(emailTextField);
		panelForm.add(passwordTextField);
		panelForm.add(passwordTextFieldValid);
		panelForm.add(userRoleChoice);
		add(errorFeedBackPanel);
		add(succesFeedBackPanel);
		add(panelForm);

	}

}
