package ua.sustavov.repository.mock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import ua.sustavov.model.User;
import ua.sustavov.model.UserRole;
import ua.sustavov.repository.UserRepository;

public class MockUserRepository implements UserRepository, Serializable {

	private static final long serialVersionUID = -8905147330650624318L;
	private Map<Integer, User> repository = new ConcurrentHashMap<>();
	private AtomicInteger counter = new AtomicInteger(0);

	public static final int USER_ID = 1;
	public static final int ADMIN_ID = 2;

	{
		save(new User(1, "User", "Useres", "user@gmaol.com", "password", UserRole.ROLE_USER));
		save(new User(2, "Admin", "Adminers", "admin@gmaol.com", "admin", UserRole.ROLE_ADMIN));
	}

	@Override
	public User save(User user) {
		if (user.isNew()) {
			user.setId(counter.incrementAndGet());

		}
		return repository.put(user.getId(), user);
	}

	@Override
	public boolean delete(int id) {
		return repository.remove(id) != null;
	}

	@Override
	public User get(int id) {
		return repository.get(id);
	}

	@Override
	public User getByEmail(String email) {
		return getAll().stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
	}

	@Override
	public List<User> getAll() {
		return new ArrayList<>(repository.values());
	}

	@Override
	public User getByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
