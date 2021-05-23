package holik.hotel.servlet.repository;

import holik.hotel.servlet.repository.model.User;

import java.util.Optional;

/**
 * Interface for user repository.
 */
public interface UserRepository {
	void createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
