package ua.sustavov.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import ua.sustavov.model.User;
import ua.sustavov.repository.UserRepository;
import ua.sustavov.util.exception.NotFoundException;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public User create(User user) {
		Assert.notNull(user, "user must not be null");
		return repository.save(user);
	}

	@Override
	public void delete(int id) throws NotFoundException {
		repository.delete(id);
	}

	@Override
	public User get(int id) throws NotFoundException {
		return repository.get(id);
	}

	@Override
	public User getByEmail(String email) throws NotFoundException {
		return repository.getByEmail(email);
	}

	@Override
	public List<User> getAll() {
		return repository.getAll();
	}

	@Override
	public void update(User user) throws NotFoundException {
		repository.save(user);

	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public User getByName(String name) throws NotFoundException {
		return repository.getByName(name);
	}

}
