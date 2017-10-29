package ua.sustavov.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.IGenericComponent;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
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

	private List<UserRole> list = Arrays.asList(UserRole.values());

	private List<Form<User>> listForms = new ArrayList<>();

	private Map<User, Form<User>> userFormMap = new HashMap<>();

	class PanelForm extends Form<User> {

		private static final long serialVersionUID = -2194025881289182529L;

		public PanelForm(String id, IModel<User> model) {
			super(id, model);

		}

		@Override
		protected void onSubmit() {
			System.out.println("On submit save");
			User editUser = getModelObject();
			System.out.println(editUser + " password " + editUser.getPassword());
			getPage().modelChanged();
		}

		@Override
		public boolean equals(Object obj) {
			User user = getModelObject();
			User userObj = (User) ((IGenericComponent<User, Form<User>>) obj).getModelObject();
			return user.equals(userObj);
		}

		@Override
		public int hashCode() {
			User user = getModelObject();
			return user.hashCode();
		}

	}

	public EditPanel(String id) {
		super(id);
	}

	public EditPanel(String id, Model<User> model) {
		super(id, model);

		FeedbackPanel errorFeedBackPanel = new FeedbackPanel("feedback",
				new ExactErrorLevelFilter(FeedbackMessage.ERROR));
		FeedbackPanel succesFeedBackPanel = new FeedbackPanel("succes",
				new ExactErrorLevelFilter(FeedbackMessage.SUCCESS));

		add(errorFeedBackPanel);
		add(succesFeedBackPanel);

		add(new ListView<Form<User>>("listview", listForms) {

			@Override
			protected void populateItem(ListItem<Form<User>> item) {
				item.add(item.getModel().getObject());
			}
		}).setVersioned(false);

	}

	public void add(Model<User> model) {
		if (!userFormMap.containsKey(model.getObject())) {
			Form<User> panelForm = createPanelForm(model);
			listForms.add(0, panelForm);
			userFormMap.put(model.getObject(), panelForm);
		} else {
			error("User is editing");
		}

	}

	private Form<User> createPanelForm(Model<User> model) {

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

		Link<Void> cancel = new Link<Void>("cancelbutton") {

			private static final long serialVersionUID = 1608389602955679300L;

			@Override
			public void onClick() {

			}
		};
		
		panelForm.add(uidTextField);
		panelForm.add(nameTextField);
		panelForm.add(surnameTextField);
		panelForm.add(emailTextField);
		panelForm.add(passwordTextField);
		panelForm.add(passwordTextFieldValid);
		panelForm.add(userRoleChoice);
		panelForm.add(cancel);

		return panelForm;
	}

	public void deleteForm(User user) {
		Form<User> userForm = userFormMap.get(user);
		listForms.remove(userForm);
		userFormMap.remove(user);
	}

	

}
