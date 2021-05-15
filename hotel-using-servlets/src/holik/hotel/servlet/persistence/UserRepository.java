package holik.hotel.servlet.persistence;

import java.util.Optional;

import holik.hotel.servlet.exception.DaoException;
import holik.hotel.servlet.model.User;

public interface UserRepository {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
