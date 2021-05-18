package holik.hotel.servlet.service.impl;

import java.util.Optional;

import holik.hotel.servlet.repository.model.User;
import holik.hotel.servlet.repository.UserRepository;
import holik.hotel.servlet.repository.impl.DefaultUserRepository;
import holik.hotel.servlet.service.UserService;

/**
 * Default realization of user service.
 */
public class DefaultUserService implements UserService {
	private final UserRepository userRepository;
	
	public DefaultUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public boolean createUser(User user) {
		return userRepository.createUser(user);
	}

	@Override
	public Optional<User> getUserById(int id) {
		return userRepository.getUserById(id);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
}
