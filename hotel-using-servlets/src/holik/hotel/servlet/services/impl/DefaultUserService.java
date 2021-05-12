package holik.hotel.servlet.services.impl;

import java.util.Optional;

import holik.hotel.servlet.models.User;
import holik.hotel.servlet.persistence.UserRepository;
import holik.hotel.servlet.persistence.impl.DefaultUserRepository;
import holik.hotel.servlet.services.UserService;

public class DefaultUserService implements UserService {

	private final UserRepository userRepository;
	
	public DefaultUserService() {
		userRepository = new DefaultUserRepository();
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