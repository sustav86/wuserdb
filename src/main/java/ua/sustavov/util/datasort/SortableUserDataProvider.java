package ua.sustavov.util.datasort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import ua.sustavov.model.User;
import ua.sustavov.view.UsersPage;

public class SortableUserDataProvider extends SortableDataProvider<User, String> {

	private List<User> listUsers; 
	
	public SortableUserDataProvider(List<User> listUsers)
    {
		this.listUsers = listUsers;
		
        setSort("id", SortOrder.ASCENDING);
    }
	
	@Override
	public Iterator<? extends User> iterator(long first, long count) {
		
		Collections.sort(listUsers, new Comparator<User>() {

			public int compare(User o1, User o2) {
				int dir = getSort().isAscending() ? 1 : -1;

				if ("id".equals(getSort().getProperty())) {
					return dir * (o1.getId().compareTo(o2.getId()));
				} else {
					return dir * (o1.getName().compareTo(o2.getName()));
				}
			}
		});

		return listUsers.subList((int) first, (int) Math.min(first + count, listUsers.size())).iterator();
	}

	@Override
	public long size() {
		return listUsers.size();
	}

	@Override
	public IModel<User> model(User object) {
		return Model.of(object);
	}

}
