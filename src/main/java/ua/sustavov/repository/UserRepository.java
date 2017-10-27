package ua.sustavov.repository;

import java.util.List;

import ua.sustavov.model.User;

public interface UserRepository {
	User save(User user);

	boolean delete(int id);

	User get(int id);

	User getByEmail(String email);

	User getByName(String name);

	List<User> getAll();

}
