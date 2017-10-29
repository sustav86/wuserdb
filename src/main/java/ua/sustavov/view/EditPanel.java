package ua.sustavov.view;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import ua.sustavov.model.User;
import ua.sustavov.model.UserRole;
import ua.sustavov.view.validators.ExactErrorLevelFilter;
import ua.sustavov.view.validators.NamePolicyValidator;

public class EditPanel extends Panel {

	private static final long serialVersionUID = -4298873343828324226L;

	public EditPanel(String id, IModel<User> model) {
		super(id, model);

		modelChanged();
		List<UserRole> list = Arrays.asList(UserRole.values());

		add(new FeedbackPanel("succes", new ExactErrorLevelFilter(FeedbackMessage.SUCCESS)));
		add(new FeedbackPanel("feedback", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));

		TextField<String> idd = new TextField<String>("id", new PropertyModel<String>(model, "id"));
		idd.setEnabled(false);

		TextField<String> name = new TextField<String>("name", new PropertyModel<String>(model, "name"));
		name.setRequired(true);
		name.add(new NamePolicyValidator());

		TextField<String> surname = new TextField<>("surname");

		TextField<String> password = new TextField<String>("password", new PropertyModel<String>(model, "password"));
		TextField<String> password2 = new TextField<String>("password2", new PropertyModel<String>(model, "password"));

		TextField<String> email = new TextField<>("email");
		email.setRequired(true);
		email.add(EmailAddressValidator.getInstance());

		DropDownChoice<UserRole> userRole = new DropDownChoice<UserRole>("role",
				new PropertyModel<UserRole>(model, "role"), list);
		userRole.setRequired(true);

		Form<User> form = new Form<User>("form", new CompoundPropertyModel<User>(model)) {

			@Override
			protected void onSubmit() {
				super.onSubmit();

			}

			@Override
			protected void onValidate() {
				super.onValidate();
			}

		};

		Button button = new Button("save") {

			@Override
			public void onSubmit() {
				super.onSubmit();
				getPage().modelChanged();
			}

		};

		add(form);
		form.add(idd);
		form.add(name);
		form.add(surname);
		form.add(password);
		form.add(password2);
		form.add(email);
		form.add(userRole);
		form.add(button);

	}

}
