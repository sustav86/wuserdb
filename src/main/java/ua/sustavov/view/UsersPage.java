package ua.sustavov.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.IMarkupFragment;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ua.sustavov.CurrentSession;
import ua.sustavov.LoggedUser;
import ua.sustavov.login.ExactErrorLevelFilter;
import ua.sustavov.model.User;
import ua.sustavov.service.UserService;

public class UsersPage extends WebPage {

	private static final long serialVersionUID = 3193168181084715182L;

	@SpringBean
	private UserService userService;

	private List<User> users = userService.getAll();
	private LoggedUser loggedUser = CurrentSession.get().getLoggedUser();
	private IModel<User> currentModel;

	private void setCurrentModel(IModel<User> currentModel) {
		this.currentModel = currentModel;
	}

	private IModel<User> getCurrentModel() {
		return currentModel;
	}

	public UserService getUserService() {
		return userService;
	}

	public void updateUsers() {
		users = userService.getAll();
	}

	public boolean getAccess(IModel<User> rowModel) {
		System.out.println("getAccess");
		if (loggedUser.isAdmin() || loggedUser.getId().equals(rowModel.getObject().getId())) {
			return true;
		}

		return false;
	}

	@Override
	protected void onModelChanged() {
		System.out.println("onModelChanged()");
		System.out.println(getCurrentModel().getObject());
		super.onModelChanged();
		if (getCurrentModel().getObject() != null) {
			User changedUser = getCurrentModel().getObject();
			User seachingChangedUser = userService.get(changedUser.getId());
			if (seachingChangedUser != null) {
				userService.update(changedUser);
				success("User update successfully");
			} else {
				error("User not found!");
			}
		}
	}

	public void clearePanel(IModel<User> rowModel) {
		if (rowModel.getObject() != null) {
			User cleanUser = rowModel.getObject();
			cleanUser.setId(0);
			cleanUser.setName("");
			cleanUser.setEmail("");
			cleanUser.setPassword("");
			cleanUser.setSurname("");
			cleanUser.setRole(null);
		}

	}

	public UsersPage(final PageParameters parameters) {

		add(new FeedbackPanel("succes", new ExactErrorLevelFilter(FeedbackMessage.SUCCESS)));
		add(new FeedbackPanel("feedback", new ExactErrorLevelFilter(FeedbackMessage.ERROR)));

		EditPanel editPanel = new EditPanel("panel", new Model<User>(new User()));
		List<IColumn<User, String>> columns = new ArrayList<>();
		DefaultDataTable<User, String> defaultDataTable = new DefaultDataTable<User, String>("users", columns,
				new ContactsProvider(), 10);

		columns.add(new PropertyColumn<User, String>(Model.of("Id"), "id", "id"));
		columns.add(new PropertyColumn<User, String>(Model.of("Name"), "name", "name"));
		columns.add(new PropertyColumn<User, String>(Model.of("Surname"), "surname"));
		columns.add(new PropertyColumn<User, String>(Model.of("Email"), "email", "email"));
		columns.add(new PropertyColumn<User, String>(Model.of("Role"), "role", "role"));

		columns.add(new AbstractColumn<User, String>(new Model<String>("")) {

			@Override
			public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel) {
				cellItem.add(new Link<String>(componentId) {

					@Override
					public void onClick() {

						if (getAccess(rowModel)) {
							System.out.println("We here on save");
							setCurrentModel(rowModel);
							EditPanel newPanel = new EditPanel("panel", rowModel);
							editPanel.replaceWith(newPanel);
						} else {
							error("Accsess denied!");
						}

					}

					@Override
					public IMarkupFragment getMarkup() {
						return Markup.of("<div wicket:id='cell'><a href=\"#\" style=\"display: block\">Edit</a></div>");
					}
				});

			}

		});

		columns.add(new AbstractColumn<User, String>(new Model<String>("")) {

			@Override
			public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel) {
				cellItem.add(new Link<String>(componentId) {

					@Override
					public void onClick() {
						if (getAccess(rowModel)) {
							getUserService().delete(rowModel.getObject().getId());
							updateUsers();
							clearePanel(rowModel);
							defaultDataTable.render();
						}

					}

					@Override
					public IMarkupFragment getMarkup() {
						return Markup
								.of("<div wicket:id='cell'><a href=\"#\" style=\"display: block\">Delete</a></div>");
					}

				});

			}

		});

		add(defaultDataTable);

		add(editPanel);

	}

	private class ContactsProvider extends SortableDataProvider<User, String> {

		public ContactsProvider() {
			setSort("name", SortOrder.ASCENDING);
		}

		public IModel<User> model(User object) {
			return Model.of(object);
		}

		@Override
		public Iterator<? extends User> iterator(long first, long count) {
			List<User> data = new ArrayList<>(users);
			Collections.sort(data, new Comparator<User>() {

				public int compare(User o1, User o2) {
					int dir = getSort().isAscending() ? 1 : -1;

					if ("name".equals(getSort().getProperty())) {
						return dir * (o1.getName().compareTo(o2.getName()));
					} else {
						return dir * (o1.getEmail().compareTo(o2.getEmail()));
					}
				}
			});

			return data.subList((int) first, (int) Math.min(first + count, data.size())).iterator();
		}

		@Override
		public long size() {
			return users.size();
		}

	}

}
