package holik.hotel.servlet.service.impl;

import java.sql.SQLException;
import java.util.Optional;

import holik.hotel.servlet.model.User;
import holik.hotel.servlet.persistence.UserRepository;
import holik.hotel.servlet.persistence.impl.DefaultUserRepository;
import holik.hotel.servlet.service.UserService;

public class DefaultUserService implements UserService {

	private final UserRepository userRepository;
	
	public DefaultUserService() {
		userRepository = new DefaultUserRepository();
	}
	
	@Override
	public boolean createUser(User user) throws SQLException {
		return userRepository.createUser(user);
	}

	@Override
	public Optional<User> getUserById(int id) throws SQLException {
		return userRepository.getUserById(id);
	}

	@Override
	public Optional<User> getUserByEmail(String email) throws SQLException {
		return userRepository.getUserByEmail(email);
	}

}
