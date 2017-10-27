package ua.sustavov.service;

import java.util.List;

import ua.sustavov.model.User;
import ua.sustavov.util.exception.NotFoundException;

public interface UserService {

	User create(User user);

	void delete(int id) throws NotFoundException;

	User get(int id) throws NotFoundException;

	User getByEmail(String email) throws NotFoundException;

	User getByName(String name) throws NotFoundException;

	List<User> getAll();

	void update(User user) throws NotFoundException;

}
