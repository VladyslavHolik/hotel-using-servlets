package holik.hotel.servlet.service.impl;

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
