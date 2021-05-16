package holik.hotel.servlet.service;

import java.util.Optional;

import holik.hotel.servlet.model.User;

/**
 * Interface for user service.
 */
public interface UserService {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
