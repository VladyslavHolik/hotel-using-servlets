package holik.hotel.servlet.repository;

import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.repository.model.User;

import java.util.Optional;

/**
 * Interface for user repository.
 */
public interface UserRepository {
	void save(User user) throws EntityExistsException;
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
