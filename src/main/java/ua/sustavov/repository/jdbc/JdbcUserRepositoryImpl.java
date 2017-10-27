package ua.sustavov.repository.jdbc;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ua.sustavov.model.User;
import ua.sustavov.repository.UserRepository;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

	private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
	@Autowired
	private final JdbcTemplate jdbcTemplate;
	@Autowired
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final SimpleJdbcInsert insertUser;

	@Autowired
	public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.insertUser = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");

		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public User save(User user) {
		MapSqlParameterSource map = new MapSqlParameterSource().addValue("id", user.getId())
				.addValue("name", user.getName()).addValue("surname", user.getSurname())
				.addValue("email", user.getEmail()).addValue("password", user.getPassword())
				.addValue("role", user.getRole().toString());

		if (user.isNew()) {
			Number newKey = insertUser.executeAndReturnKey(map);
			user.setId(newKey.intValue());
		} else {
			namedParameterJdbcTemplate.update(
					"UPDATE users SET name=:name, surname=:surname, email=:email, password=:password, role=:role WHERE id=:id",
					map);
		}
		return user;
	}

	@Override
	public boolean delete(int id) {
		return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
	}

	@Override
	public User get(int id) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
		return DataAccessUtils.singleResult(users);
	}

	@Override
	public User getByEmail(String email) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
		return DataAccessUtils.singleResult(users);
	}

	@Override
	public List<User> getAll() {
		return jdbcTemplate.query("SELECT id, name, surname, email, password, role FROM users", ROW_MAPPER);
	}

	@Override
	public User getByName(String name) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE name=?", ROW_MAPPER, name);
		return DataAccessUtils.singleResult(users);
	}

}
