package holik.hotel.servlet.services.impl;

import java.util.Optional;

import holik.hotel.servlet.models.User;
import holik.hotel.servlet.persistence.UserRepository;
import holik.hotel.servlet.persistence.impl.UserRepositoryImpl;
import holik.hotel.servlet.services.UserService;

public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	public UserServiceImpl() {
		userRepository = new UserRepositoryImpl();
	}
	
	@Override
	public boolean createUser(User user) {
		return userRepository.createUser(user);
	}

	@Override
	public Optional<User> getUserById(int id) {
		return userRepository.getUserById(id);
	}

}
