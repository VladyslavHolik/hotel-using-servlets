package holik.hotel.servlet.repository;

import java.util.Optional;

import holik.hotel.servlet.repository.model.User;

/**
 * Interface for user repository.
 */
public interface UserRepository {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
