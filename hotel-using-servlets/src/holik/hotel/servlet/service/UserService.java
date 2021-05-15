package holik.hotel.servlet.service;

import java.sql.SQLException;
import java.util.Optional;

import holik.hotel.servlet.model.User;

public interface UserService {
	boolean createUser(User user);
	Optional<User> getUserById(int id);
	Optional<User> getUserByEmail(String email);
}
