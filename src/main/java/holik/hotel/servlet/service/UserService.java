package holik.hotel.servlet.service;

import java.util.Optional;

import holik.hotel.servlet.repository.exception.EntityExistsException;
import holik.hotel.servlet.repository.model.User;

/**
 * Interface for user service.
 */
public interface UserService {
	void save(User user) throws EntityExistsException;
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
	boolean areCredentialsTrue(String email, String password);
}
